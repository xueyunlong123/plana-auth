package com.scaff.plana.auth.client.feignservice;

import com.scaff.common.web.result.JSONResult;
import com.scaff.dto.UpdateUrlInfoReqDTO;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xyl on 1/8/18.
 */
@FeignClient(value = "plana-auth-center",path = "/authCenter")
public interface AuthCenterService {
    @RequestMapping(value = "/urlPermissions",method = RequestMethod.POST)
    JSONResult updateUrlPermissions(@RequestBody UpdateUrlInfoReqDTO updateUrlInfoReqDTO);
}
