package com.scaff.api.gateway.service;

import com.scaff.common.web.argsresolver.FastJson;
import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.AuthPermissionReqDTO;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xyl on 1/9/18.
 */
@FeignClient("plana-auth-center")
public interface AuthCenterFeignService {

    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    JSONResult authPermission(@FastJson AuthPermissionReqDTO dto);
}
