package com.scaff.plana.auth.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.scaff.common.web.argsresolver.FastJson;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.GetUsernameResDTO;
import com.scaff.dto.LoginDTO;
import com.scaff.entity.User;
import com.scaff.utils.encrypt.HashUtil;
import com.scaff.utils.exception.WebBasicCodeEnum;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jodd.util.Base64;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/8/18.
 */
@RestController
@Slf4j
public class JwtController {

    @Autowired
    private Datastore datastore;

    @Autowired
    private RedissonClient redissonClient;

    private static final Object PRESENT = null;

    @Value("${plana.jwt.map:plana.jwt.map}")
    private String jwtMap;

    @Value("${plana.jwt.key:plana.jwt.key}")
    private String jwtKey;


    @Value("${plana.jwt.expired.time:60000}")
    private long jwtExpiredTime;

    @PostMapping("/login")
    public JSONResult login(@FastJson LoginDTO loginDTO){
        log.info(JSONObject.toJSONString(loginDTO));
        User user = datastore.get(
                User.builder()
                        .username(loginDTO.getUsername())
                        .build()
        );
        String decryptPassword = HashUtil.sha1(Base64.decodeToString(loginDTO.getPassword()),user.getSalt());

        if (StringUtils.equals(decryptPassword,user.getPassword())){
            String jwt = Jwts.builder()
                    .setSubject(user.getUsername())
                    .signWith(SignatureAlgorithm.HS256, jwtKey)
                    .compact();
            redissonClient.getMapCache(jwtMap).put(user.getUsername(),PRESENT,jwtExpiredTime, TimeUnit.SECONDS);
            return JSONResult.ok(
                    new JSONObject(){{
                        put("jwt",jwt);
                    }}
            );
        }else {
            return JSONResult.error(WebBasicCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    @GetMapping("/jwtCheck/{jwt}")
    public JSONResult check(@PathVariable("jwt") String jwt){
        String username = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwt).getBody().getSubject();
        return
                redissonClient.getMapCache(jwtMap).containsKey(username) ?
                        JSONResult.ok() : JSONResult.error(WebBasicCodeEnum.LOGIN_EXPIRED);
    }

    @GetMapping("/{jwt}/username")
    public JSONResult getUsername(@PathVariable("jwt") String jwt){
        String username = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwt).getBody().getSubject();
        return JSONResult.ok(GetUsernameResDTO.builder().username(username).build());
    }

}
