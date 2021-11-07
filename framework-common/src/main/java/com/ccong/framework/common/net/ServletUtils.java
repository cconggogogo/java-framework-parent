package com.ccong.framework.common.net;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ccong.framework.common.json.JsonUtils;
import com.ccong.framework.common.lang.IdGenerator;
import com.ccong.framework.common.lang.StringUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletUtils {

    private static final Logger log = LoggerFactory.getLogger(ServletUtils.class);

    private ServletUtils() {

    }


    public static String getTraceId(HttpServletRequest request, boolean returnNewUUIDIfNotExistsInHeader) {
        String traceId = request.getHeader("X-Request_id");
        if (StringUtils.isBlank(traceId) && returnNewUUIDIfNotExistsInHeader) {
            traceId = IdGenerator.uuid();
        }

        return StringUtils.defaultString(traceId);
    }

    private static Map<String, String> getHeadersInfo(HttpServletRequest httpServletRequest) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private static String parseIp(String ip) {
        if (!ip.contains(",")) {
            return ip;
        }
        return ip.substring(0, ip.indexOf(","));
    }

    /*
    *从请求头中获取IP地址
    */
    public static String getClientIp(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return "";
        }

        String ip;
        if (!StringUtils.isEmpty(httpServletRequest.getHeader("cdn-src-ip"))) {
            ip = httpServletRequest.getHeader("cdn-src-ip");
            debug("getIpAddress|HTTP_CDN_SRC_IP|,{}", ip);
        } else if (!StringUtils.isEmpty(httpServletRequest.getHeader("x-real-ip"))) {
            ip = httpServletRequest.getHeader("x-real-ip");
            debug("getIpAddress|HTTP_X_REAL_IP|,{}", ip);
        } else if (!StringUtils.isEmpty(httpServletRequest.getHeader("x-forwarded-for"))) {
            ip = httpServletRequest.getHeader("x-forwarded-for");
            debug("getIpAddress|x-forwarded-for|,{}", ip);
        } else if (!StringUtils.isEmpty(httpServletRequest.getRemoteHost())) {
            ip = httpServletRequest.getRemoteHost();
            debug("getIpAddress|getRemoteHost|,{}", ip);
        } else {
            ip = IpUtils.UNKNOWN_IP_ADDRESS;
            log.warn("getIpAddress|no ip found|");
        }

        return parseIp(ip);
    }

    public static String dump(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("requestURI", request.getRequestURI());
        context.put("requestMethod", request.getMethod());
        context.put("requestHeader", dumpRequestHeader(request));
        context.put("requestParameterMap", request.getParameterMap());
        context.put("requestCookies", dumpRequestCookies(request));
        return JsonUtils.toJson(context);
    }

    public static String dumpRequestHeader(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        if (request.getHeaderNames() != null) {
            Enumeration<String> iterator = request.getHeaderNames();
            if (iterator != null) {
                while (iterator.hasMoreElements()) {
                    String key = iterator.nextElement();
                    String value = request.getHeader(key);
                    map.put(key, value);
                }
            }
        }
        return JsonUtils.toJson(map);
    }

    public static Map<String, String> dumpRequestCookies(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String key = cookie.getName();
                String value = cookie.getValue();
                map.put(key, value);
            }
        }
        return map;
    }


    private static void debug(String msg, Object ... args) {
        if (log.isDebugEnabled()) {
            log.debug(msg, args);
        }
    }
}
