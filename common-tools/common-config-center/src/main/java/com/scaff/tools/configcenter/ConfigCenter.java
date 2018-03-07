package com.scaff.tools.configcenter;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/4/18.
 */
@Component
@Slf4j
public class ConfigCenter {

    private final static String DEFAULT_VALUE = "undefined";
    private Config config;

    public ConfigCenter() {
        ConfigChangeListener changeListener = changeEvent -> {
            log.info("Changes for namespace {}", changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                log.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
                        change.getPropertyName(), change.getOldValue(), change.getNewValue(),
                        change.getChangeType());
            }
        };
        config = ConfigService.getAppConfig();
        config.addChangeListener(changeListener);
    }

    public String getConfig(String key) {
        String result = config.getProperty(key, DEFAULT_VALUE);
        log.info(String.format("Loading key : %s with value: %s", key, result));
        return result;
    }
}
