package com.scaff.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xyl on 1/8/18.
 */
@Entity("plana-auth-permission")
@Data@Builder@AllArgsConstructor@NoArgsConstructor
public class UrlPermission implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String path;

    private String method;

    private String application;

}
