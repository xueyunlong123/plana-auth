package com.scaff.enums.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by xyl on 11/14/17.
 */
@AllArgsConstructor
public enum ParamType {
    HEADER("header"),
    BODY("body"),
    QUERY("query"),
    PATH("path"),
    FORM("form"),
    ;

    @Getter
    private String value;
}
