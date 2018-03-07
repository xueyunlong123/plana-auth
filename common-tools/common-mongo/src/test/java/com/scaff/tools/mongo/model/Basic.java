package com.scaff.tools.mongo.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chenwen on 17/3/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embedded
public class Basic implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号所在地区
     */
    @Property("area_code")
    private String areaCode;

    /**
     * 手机号所属运营商
     */
    private String operator;
}
