package com.scaff.data.analyze;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by xyl on 11/10/17.
 */
@RestController
@Slf4j
public class AnalyzeController {

    @PostMapping("/analyze")
    public boolean analyze(){
        log.info("需要分析的人是：{}");
        return true;
    }


}
