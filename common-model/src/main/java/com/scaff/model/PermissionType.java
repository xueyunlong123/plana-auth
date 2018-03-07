package com.scaff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by xyl on 1/8/18.
 */
@AllArgsConstructor
public enum PermissionType {
    URL(1),
    SERVICE(2),
    ;

    @Getter
    private int code;
}
