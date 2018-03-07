package com.scaff.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scaff.utils.exception.BaseException;
import com.scaff.utils.exception.WebBasicCodeEnum;
import com.scaff.utils.exception.WebException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;


/**
 * Http utils
 */
@Slf4j
public class HttpUtils {
    /**
     * log based on slf4j
     */

    public static String getRealIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-UserLoginDTO-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-UserLoginDTO-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        log.info("ip is " + ip);
        return ip;
    }

    public static String getRealIpAddress(HttpHeaders headers) {

        String ip = headers.getFirst("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-UserLoginDTO-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-UserLoginDTO-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }

        log.info("ip is " + ip);
        return ip;
    }
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getRequestBodyAsJson(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        } catch (IOException e) {
            throw new WebException(WebBasicCodeEnum.ARGUMENT_PARSE_ERROR,e);
        }
    }

    public static String getQueryStringAsJson(HttpServletRequest request) {
        try {
            String queryStr = request.getQueryString();
            if (StringUtils.isNotEmpty(queryStr)) {
                return getRequestBody(URLDecoder.decode(request.getQueryString(), request.getCharacterEncoding()));
            } else {
                return "{}";
            }
        } catch (UnsupportedEncodingException e) {
            throw new WebException(WebBasicCodeEnum.ARGUMENT_PARSE_ERROR,e);
        }
    }

    public static <T> T getModelFromQueryString(String queryStringJson, Class<T> type){
        T result = JSONObject.parseObject(queryStringJson, type);
        ValidateUtil.validate(result);
        return result;
    }

    public static <T> T getModelFromQueryString(HttpServletRequest request, Class<T> type){
        T result = JSONObject.parseObject(getQueryStringAsJson(request), type);
        ValidateUtil.validate(result);
        return result;
    }

    public static <T> T getModelFromRequest(HttpServletRequest request, Class<T> type){
        T result = null;
        try {
            String requestBody = null;
            if (request.getMethod().equalsIgnoreCase(HttpMethod.HTTP_METHOD.HTTP_POST.getString())) {
                if (request.getContentType().startsWith(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())){
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    JSONObject json = new JSONObject();
                    for(String key : parameterMap.keySet()){
                        String[] value = parameterMap.get(key);
                        if (value != null && value.length > 0) {
                            json.put(key, parameterMap.get(key)[0]);
                        }
                    }
                    requestBody = json.toJSONString();
                }else if (request.getContentType().startsWith(ContentType.TEXT_XML.getMimeType())){
                    JAXBUtil<T> jaxbUtil = new JAXBUtil<>();
                    result = jaxbUtil.unmarshal(type, IOUtils.toString(request.getInputStream(), request.getCharacterEncoding()));
                }else {
                    requestBody = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
                    if (StringUtils.isNotEmpty(requestBody) && !requestBody.startsWith("{")) {
                        requestBody = getRequestBody(requestBody);
                    }
                }
            } else if (request.getMethod().equalsIgnoreCase(HttpMethod.HTTP_METHOD.HTTP_GET.getString())) {
                    requestBody = getRequestBody(URLDecoder.decode(request.getQueryString(), request.getCharacterEncoding()));
            }
            if (result == null) {
                result = JSON.parseObject(requestBody, type);
            }
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new WebException(WebBasicCodeEnum.ARGUMENT_PARSE_ERROR,e);
        }
        ValidateUtil.validate(result);
        return result;
    }

    public static Charset getCharset(MediaType mediaType){
        if (mediaType != null && mediaType.getCharset() != null){
            return mediaType.getCharset();
        }else {
            return StandardCharsets.UTF_8;
        }
    }

    public static String getReceiveParam(HttpServletRequest request) {
        BufferedReader bufferedReader = null;
        StringBuilder data = new StringBuilder();
        try {
            request.setCharacterEncoding("UTF-8");
            bufferedReader = request.getReader();
            if (bufferedReader == null) {
                return null;
            }
            String line;
            while ((line = bufferedReader.readLine())!=null){
                data.append(line);
            }
        } catch (Exception e) {
            log.error("ͨ解析参数异常", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ie) {
                log.error("ͨ解析参数异常", ie);
            }
        }
        return data.toString();
    }

    public static String getRequestBody(String requestBody) {
        if (StringUtils.isEmpty(requestBody)) {
            throw new WebException(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR);
        }
        Pattern pattern = Pattern.compile("(req|param)=\\{(.+:.+)+\\}");
        Matcher matcher = pattern.matcher(requestBody);
        if (matcher.find()) {
            return requestBody.replaceFirst("(req|param)=","");
        }

        JSONObject requestBodyJSON = null;
        matcher = Pattern.compile("(([a-zA-Z0-9_]+)=([^&]+))&?+").matcher(requestBody);
        while (matcher.find()) {
            if (requestBodyJSON == null) {
                requestBodyJSON = new JSONObject();
            }
            String key = matcher.group(2);
            String value = matcher.group(3);
            if (value.startsWith("[") && value.endsWith("]")){
                requestBodyJSON.put(key, JSONArray.parseArray(value));
            }else if (value.startsWith("{") && value.endsWith("}")){
                requestBodyJSON.put(key, JSONObject.parseObject(value));
            }else {
                requestBodyJSON.put(key, value);
            }
        }
        if (requestBodyJSON == null) {
            log.error(requestBody);
            throw new WebException(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR);
        }
        return JSON.toJSONString(requestBodyJSON);
    }

    public static void main(String[] args) {
        String requestBody = "timestamp=1234567891023&signature=A82F684D47FE99528209B6EC0A56F1C0";
        JSONObject requestBodyJSON = null;
        Matcher matcher = Pattern.compile("(([a-zA-Z0-9_]+)=([^&]+))&?+").matcher(requestBody);
        while (matcher.find()) {
            if (requestBodyJSON == null) {
                requestBodyJSON = new JSONObject();
            }
            requestBodyJSON.put(matcher.group(2), matcher.group(3));
        }
        if (requestBodyJSON == null) {
            log.error(requestBody);
            throw new WebException(WebBasicCodeEnum.ILLEGAL_ARGUMENT_ERROR);
        }
        log.error(JSON.toJSONString(requestBodyJSON));
    }
}
