package com.scaff.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xyl on 1/9/18.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddRoleReqDTO {

    private String roleType;

    private int height;

}
