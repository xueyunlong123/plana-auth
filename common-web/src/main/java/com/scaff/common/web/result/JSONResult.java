package com.scaff.common.web.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.scaff.enums.date.DateFormatterEnum;
import com.scaff.utils.exception.IErrorCode;
import com.scaff.utils.json.JacksonUtil;

import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Date;

import lombok.NoArgsConstructor;

/**
 * Created by cw on 15-11-20.
 */
@NoArgsConstructor
public class JSONResult extends Result<JSONObject> {

    private static final long serialVersionUID = 1L;

    private JSONResult(IErrorCode iErrorCode, boolean success, JSONObject data) {
        this(iErrorCode.getCode(), iErrorCode.getMessage(),success,data);
    }

    private JSONResult(String code, String message, boolean success, JSONObject data) {
        super.setCode(code);
        super.setMessage(message);
        super.setSuccess(success);
        super.setData(data);
    }

    public static JSONResult build(String code, String message, boolean success, JSONObject data){
        return new JSONResult(code,message,success,data);
    }

    public static JSONResult ok() {
        return ok(new JSONObject());
    }

    public static JSONResult ok(Object object){
        return ok(JSON.parseObject(toJSONString(object, DateFormatterEnum.DEFAULT)));
    }

    public static JSONResult ok(JSONObject data) {
        return new JSONResult(IErrorCode.OK, true, JSON.parseObject(toJSONString(data, DateFormatterEnum.DEFAULT)));
    }

    public static JSONResult ok(Object object,String dateFormat) {
        return ok(JSON.parseObject(toJSONString(object, dateFormat)));
    }

    public static JSONResult ok(Object object,SerializeConfig config){
        return ok(JSON.parseObject(toJSONString(object,null,config)));
    }


    public static JSONResult ok(JSONObject data, String dateFormat) {
        return new JSONResult(IErrorCode.OK, true, JSON.parseObject(toJSONString(data,dateFormat)));
    }

    //服务异常
    public static JSONResult error() {
        return error(IErrorCode.ERROR);
    }

    public static JSONResult error(IErrorCode iErrorCode) {
        return error(iErrorCode, new JSONObject());
    }

    public static JSONResult error(IErrorCode iErrorCode, JSONObject data) {
        return new JSONResult(iErrorCode, false, data);
    }

    public static JSONResult error(String code, String message) {
        return new JSONResult(code, message, false, new JSONObject());
    }

    public static JSONResult custom(String code, String message, boolean success, JSONObject data){
        return new JSONResult(code,message,success,data);
    }

    public JSONResult put(String key, Object value) {
        if (super.getData() == null) {
            super.setData(new JSONObject());
        }
        super.getData().put(key, value);
        return this;
    }


    public String buildJsonString() {
        return toJSONString(this, DateFormatterEnum.DEFAULT);
    }

    private static String toJSONString(Object object,String dateFormat){
        return toJSONString(object,dateFormat, SerializeConfig.getGlobalInstance());
    }

    private static String toJSONString(Object object,String dateFormat,SerializeConfig config){
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        if (object == null){
            return new JSONObject().toJSONString();
        }else if (object instanceof String || object instanceof Long || object instanceof Integer || object instanceof Double || object instanceof Date){
            JSONObject result = new JSONObject();
            result.put("value",object);
            return JSON.toJSONString(result, config, null, dateFormat, JSON.DEFAULT_GENERATE_FEATURE);
        }else if (object instanceof Collection){
            JSONObject result = new JSONObject();
            result.put("list",object);
            return JSON.toJSONString(result, config, null, dateFormat, JSON.DEFAULT_GENERATE_FEATURE);
        } else {
            return JSON.toJSONString(object, config, null, dateFormat, JSON.DEFAULT_GENERATE_FEATURE);
        }
    }

    public ModelAndView buildModelAndView() {
        return new ModelAndView(new FastJsonJsonView(), JacksonUtil.jsonToMap(this.buildJsonString()));
    }
}
