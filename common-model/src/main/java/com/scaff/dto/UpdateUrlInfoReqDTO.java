package com.scaff.dto;


import com.scaff.model.UrlPermission;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xyl on 1/8/18.
 */
@AllArgsConstructor@NoArgsConstructor@Data@Builder
public class UpdateUrlInfoReqDTO {

    private List<UrlPermission> urlPermissions;

}
