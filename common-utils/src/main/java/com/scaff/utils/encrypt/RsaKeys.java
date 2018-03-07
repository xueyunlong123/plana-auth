package com.scaff.utils.encrypt;

import lombok.Builder;
import lombok.Data;

/**
 * Created by xyl on 12/26/17.
 */
@Data@Builder
public class RsaKeys {
    private String primaryKey;
    private String publicKey;
}
