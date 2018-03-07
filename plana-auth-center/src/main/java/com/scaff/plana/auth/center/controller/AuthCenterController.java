package com.scaff.plana.auth.center.controller;

import com.scaff.common.web.argsresolver.FastJson;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.AuthPermissionReqDTO;
import com.scaff.model.UrlPermission;
import com.scaff.utils.exception.WebBasicCodeEnum;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/8/18.
 */
@RestController
@Slf4j
@RequestMapping("/authCenter")
public class AuthCenterController {

    @Autowired
    Datastore datastore;

    @PostMapping("/auth")
    public JSONResult authPermission(@FastJson AuthPermissionReqDTO dto){
        UrlPermission permission = datastore.createQuery(UrlPermission.class)
                .field("application").equal(dto.getApplication())
                .field("path").equal(dto.getPath())
                .get();
        return permission == null ?
                JSONResult.error(WebBasicCodeEnum.PERMISSION_ERROR) : JSONResult.ok();
    }
}
