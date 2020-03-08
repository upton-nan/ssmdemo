package com.app.utils.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @ClassName: CookieUtils
 * @Description: Cookie的工具类
 * @author lyn
 * @date 2019年8月5日
 *
 */
public final class CookieUtils {

   

	/**
	 * 
	 * @Title: addCookie
	 * @Description: 设置 Cookie, 过期时间自定义
	 * @param response
	 * @param name 名称
	 * @param value 值
	 * @param path
	 * @param maxAge 过期时间, 单位秒
	 * @return
	 */
    public static Cookie addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(maxAge);
            if (null != path) {
                cookie.setPath(path);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 
     * @Title: addCookie
     * @Description: 设置 Cookie, 过期时间自定义
     * @param response
     * @param name 名称
     * @param value 值
     * @param domain
     * @param path
     * @param maxAge 过期时间, 单位秒
     * @return
     */
    public static Cookie addCookie(HttpServletResponse response, String name, String value,String domain, String path, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            if(domain != null && !"".equals(domain.trim())) {
                cookie.setDomain(domain);
            }
            cookie.setMaxAge(maxAge);
            if (null != path) {
                cookie.setPath(path);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 
     * @Title: addCookies
     * @Description: 设置 Cookies, 过期时间自定义
     * @param response 响应对象
     * @param values 值
     * @param path 路径
     * @param maxAge 过期时间, 单位秒
     * @return
     */
    public static ArrayList<Cookie> addCookies(HttpServletResponse response, Map<String, String> values, String path, int maxAge) {
        Set<Map.Entry<String, String>> entries = values.entrySet();
        ArrayList<Cookie> cookies = new ArrayList<Cookie>();
        try {
            for (Map.Entry<String, String> entry : entries) {
                Cookie cookie = new Cookie(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
                cookie.setMaxAge(maxAge);
                if (null != path) {
                    cookie.setPath(path);
                }
                response.addCookie(cookie);
                cookies.add(cookie);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookies;
    }

    /**
     * 
     * @Title: getCookie
     * @Description: 获得指定Cookie的值
     * @param request
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

   
    /**
     * 
     * @Title: getCookie
     * @Description: 获得指定Cookie的值，并删除。
     * @param request
     * @param response
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, true);
    }


    /**
     * 
     * @Title: getCookie
     * @Description: 获得指定Cookie的值
     * @param request 请求对象
     * @param response 响应对象
     * @param name 名字
     * @param isRemoved 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemoved) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isRemoved) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                    return value;
                }
            }
        }
        return value;
    }
}
