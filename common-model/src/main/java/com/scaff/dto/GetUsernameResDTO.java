package com.scaff.dto;

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
public class GetUsernameResDTO {
    private String username;
}
