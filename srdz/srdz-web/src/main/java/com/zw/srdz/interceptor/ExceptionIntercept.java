package com.zw.srdz.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 全局异常处理拦截器
 * @author zhangwei
 *
 */
public class ExceptionIntercept extends SimpleMappingExceptionResolver{

	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		return null;
	}
}
