package com.scaff.tools.mongo.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Property;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by chenwen on 17/3/30.
 */
@Entity("mobile")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileTestModel implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Id
    @Property("_id")
    private String id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 手机号基本信息
     */
    @Embedded
    private Basic basic;

    /**
     * 更新时间
     */
    @Property("update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Property("create_time")
    private Date createTime;


    @PrePersist
    void setUpdateTime(){
        updateTime = new Date();
    }

    @PrePersist
    void setCreateTime(){
        if (createTime == null) {
            createTime = new Date();
        }
    }
}
