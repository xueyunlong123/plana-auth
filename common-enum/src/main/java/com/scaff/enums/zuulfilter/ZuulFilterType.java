package com.scaff.enums.zuulfilter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by xyl on 11/3/17.
 */
@AllArgsConstructor
public enum ZuulFilterType {
    PRE("pre");

    @Getter
    private String value;
}
