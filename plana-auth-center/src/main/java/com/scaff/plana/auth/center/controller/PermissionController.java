package com.scaff.plana.auth.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.scaff.common.web.argsresolver.FastJson;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.UpdateUrlInfoReqDTO;
import com.scaff.model.UrlPermission;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/10/18.
 */
@RestController
@Slf4j
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    Datastore datastore;

    @PostMapping
    public void updateUrlInfo(@FastJson UpdateUrlInfoReqDTO updateUrlInfoReqDTO){
        log.info(JSONObject.toJSONString(updateUrlInfoReqDTO));
        Query<UrlPermission> query = datastore.createQuery(UrlPermission.class)
                .field("application").equal(updateUrlInfoReqDTO.getUrlPermissions().get(0).getApplication());
        datastore.delete(query);
        updateUrlInfoReqDTO.getUrlPermissions().forEach(datastore::save);
    }

    @GetMapping
    public JSONResult getAllPermissions(){
        return JSONResult.ok(
                datastore.createQuery(UrlPermission.class).asList()
        );
    }
}
