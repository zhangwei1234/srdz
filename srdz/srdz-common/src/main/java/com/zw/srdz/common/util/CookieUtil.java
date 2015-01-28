package com.zw.srdz.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie 处理的工具类
 * 
 * @author zhangwei
 *
 */
public class CookieUtil {

	
	/**
	 * 获取cookie值
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest req,String name){
		
		try {
			
			Cookie [] cs = req.getCookies();
			if(cs == null){
				return null;
			}
			
			for (Cookie ck : cs){
				if(ck.getName().equals(name)){
					return ck.getValue();
				}
			}
			
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	/**
	 * 添加cookie信息
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response,String key,String value) {
        try {
            Cookie cookie = new Cookie(key, value);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            ;
        }
    }
	
	/**
	 * 删除cookie
	 * @param response
	 * @param domain
	 * @param key
	 */
	 public static void delCookie(HttpServletResponse response,String domain,String key){
	        try {
	            Cookie cookie = new Cookie(key, null);
	            cookie.setMaxAge(0);
	            cookie.setPath("/");
	            if(domain != null) {
	                cookie.setDomain(domain);
	            }
	            response.addCookie(cookie);
	        } catch (Exception e) {
	            ;
	        }
	    }
	
}
