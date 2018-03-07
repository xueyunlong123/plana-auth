package com.scaff.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xyl on 1/9/18.
 */
@Data@Builder@AllArgsConstructor@NoArgsConstructor
public class LoginDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
