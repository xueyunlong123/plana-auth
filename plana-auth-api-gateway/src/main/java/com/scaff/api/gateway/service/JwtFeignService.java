package com.scaff.api.gateway.service;

import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.LoginDTO;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xyl on 1/9/18.
 */
@FeignClient("plana-auth-center")
public interface JwtFeignService {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    JSONResult login(@RequestBody LoginDTO loginDTO);

    @RequestMapping(value = "/jwtCheck/{jwt}",method = RequestMethod.GET)
    JSONResult check(@PathVariable("jwt") String jwt);

    @RequestMapping(value = "/{jwt}/username",method = RequestMethod.GET)
    JSONResult getUsername(@PathVariable("jwt") String jwt);

}
