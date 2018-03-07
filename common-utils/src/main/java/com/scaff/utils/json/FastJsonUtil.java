package com.scaff.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by xyl on 12/26/17.
 */
public class FastJsonUtil {

    public static <T> T parseObject(Object o, Class<T> clazz){
        return JSONObject.parseObject(JSON.toJSONString(o),clazz);
    }


}
