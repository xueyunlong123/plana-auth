package com.scaff.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.scaff.enums.date.DateFormatterEnum;
import com.scaff.model.AccessRoleType;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xyl on 1/9/18.
 */
@Data@Builder@AllArgsConstructor@NoArgsConstructor
@Entity
public class User implements AccessRoleType{
    @Id
    private String id;

    private String username;

    private String password;

    private String salt;

    private List<String> roleType;

    @JSONField(format = DateFormatterEnum.DEFAULT)
    private String createTime;

    @JSONField(format = DateFormatterEnum.DEFAULT)
    private String updateTime;

    @PrePersist
    private void setDefaultValue(){

        Date currentDate = new Date();

        updateTime = new SimpleDateFormat(DateFormatterEnum.DEFAULT).format(currentDate);
        if (createTime == null) {
            createTime = new SimpleDateFormat(DateFormatterEnum.DEFAULT).format(currentDate);
        }
    }

}
