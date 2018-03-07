package com.scaff.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xyl on 1/8/18.
 */
@Data@Builder@AllArgsConstructor@NoArgsConstructor
@Entity("plana-auth-role")
public class Role implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String roleId;

    @Property("role_type")
    private String roleType;

    @Property("hold_permission_ID")
    private List<String> holdPermissionID;

    private int height;
}
