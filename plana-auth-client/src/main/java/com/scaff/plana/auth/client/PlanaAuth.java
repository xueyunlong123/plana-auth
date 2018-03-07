package com.scaff.plana.auth.client;

import com.netflix.discovery.EurekaClient;
import com.scaff.dto.UpdateUrlInfoReqDTO;
import com.scaff.model.UrlPermission;
import com.scaff.plana.auth.client.feignservice.AuthCenterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/8/18.
 */
@Slf4j
@EnableFeignClients
public class PlanaAuth {
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    AuthCenterService authManagerService;

    @Autowired
    EurekaClient eurekaClient;

    @PostConstruct
    public void prepareUrl() {
      log.info(requestMappingHandlerMapping.toString());

      UpdateUrlInfoReqDTO updateUrlInfoReqDTO = UpdateUrlInfoReqDTO.builder()
              .urlPermissions(
                      requestMappingHandlerMapping.getHandlerMethods().entrySet().stream().map(
                              requestMappingInfoHandlerMethodEntry -> UrlPermission.builder()
                              .application(eurekaClient.getApplicationInfoManager().getEurekaInstanceConfig().getAppname())
                              .path((String) requestMappingInfoHandlerMethodEntry.getKey().getPatternsCondition().getPatterns().toArray()[0])
                              .method(requestMappingInfoHandlerMethodEntry.getValue().getMethod().toString())
                              .build())
                              .collect(Collectors.toList())
              )
              .build();

      authManagerService.updateUrlPermissions(updateUrlInfoReqDTO);
    }
}
