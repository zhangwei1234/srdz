package com.zw.srdz.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zw.srdz.author.Author;
import com.zw.srdz.author.AuthorType;
import com.zw.srdz.common.Constants;
import com.zw.srdz.common.util.CookieUtil;

/**
 * 授权,认证控制拦截器<br>
 * 
 * <li> 用户登录检查
 * <li> 黑名单检查
 * 
 * @author zhangwei
 *
 */
public class AuthorIntercept extends HandlerInterceptorAdapter{

	Logger LOG = LoggerFactory.getLogger(AuthorIntercept.class);
	
	@Value(value="${login_url}")
	private String login_url;
	
	private ThreadLocal<String> host = new ThreadLocal<String>();
	private ThreadLocal<Long> timer = new ThreadLocal<Long>();
	
	/**
	 * 预处理拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		timer.set(System.currentTimeMillis());
		String hostName = request.getServerName();
		host.set(hostName);
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		HandlerMethod method = (HandlerMethod) handler;
		Author methodAuthor = method.getMethodAnnotation(Author.class);
		Author classAuthor = method.getBean().getClass().getAnnotation(Author.class);
		
		if (null == methodAuthor && null == classAuthor){//不需要任何检查
			return true;
		}
		
		//获取要检查的类型
		AuthorType [] methodTypes = null;
		AuthorType [] classTypes = null;
		
		if(null != methodAuthor){
			methodTypes = methodAuthor.type();
		}
		
		if(null != classAuthor){
			classTypes = classAuthor.type();
		}
		
		
		//检查用户是否登陆
		if((isAuthorType(methodTypes, AuthorType.LOGIN_USER) || isAuthorType(classTypes, AuthorType.LOGIN_USER)) && !isAuthorType(methodTypes, AuthorType.LOGIN_USER_NOT)){
			
			String cookie_value = CookieUtil.getCookie(request, "topic.com");
			
			if(StringUtils.isEmpty(cookie_value)){
				response.sendRedirect(login_url);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 后处理拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
		try {
			Map<String,Object> models = modelAndView.getModel();
			if(null != models){
				Object bascUrl = models.get(Constants.BASE_URL_NAME);
				Object bascStaticUrl = models.get(Constants.BASE_STATIC_URL_NAME);
				
				if(null != bascUrl){
					models.put(Constants.BASE_URL_NAME, bascUrl.toString().replaceAll(Constants.DOMAIN_PROFIX, host.get()));
				}
				if(null != bascStaticUrl){
					models.put(Constants.BASE_STATIC_URL_NAME, bascStaticUrl.toString().replaceAll(Constants.DOMAIN_PROFIX, host.get()));
				}
			}
		} catch (Exception e) {
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 返回值处理拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		String url      = request.getRequestURL().toString();
		String queryStr = request.getQueryString();
		
		LOG.info("request--->"+url+"?"+queryStr+", timer:"+(System.currentTimeMillis()-timer.get()));
		
		super.afterCompletion(request, response, handler, ex);
	}
	
	//判断是否存在要检查的类型
	private boolean isAuthorType(AuthorType[] types , AuthorType type){
		
		if(types != null && types.length>0 && type != null){
			
			for(AuthorType t:types){
				if(t.getVal() == type.getVal()){
					return true;
				}
			}
		}
		
		return false;
		
	}
}
