package com.zw.srdz.interceptor;

import javax.annotation.Resource;
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
import com.zw.srdz.common.util.CookieUtil;
import com.zw.srdz.domain.author.AuthorContext;
import com.zw.srdz.domain.author.LoginContext;
import com.zw.srdz.domain.author.LoginContextEncrypt;

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
	
	@Value(value="${base_url}")
	protected String base_url;
	
	@Value(value="${cookie.name}")
	private String cookie_name;
	
	private ThreadLocal<Long> timer = new ThreadLocal<Long>();
	@Resource private AuthorContext authorLocal;
	
	/**
	 * 预处理拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		timer.set(System.currentTimeMillis());
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
		
		//判断是否需要设置cookie
		if(isAuthorType(methodTypes, AuthorType.LOGIN_CUSTOMER_COOKIE) || isAuthorType(classTypes, AuthorType.LOGIN_CUSTOMER_COOKIE)){
			String cookie_value = CookieUtil.getCookie(request, cookie_name);
			if(!StringUtils.isEmpty(cookie_value)){
				LoginContext context = LoginContextEncrypt.decodeContext(cookie_value);
				CookieUtil.addCookie(response, cookie_name, LoginContextEncrypt.encodingContext(context));
				authorLocal.getLocal().set(context);
			}
		}
		
		//检查普通用户是否登陆
		if((isAuthorType(methodTypes, AuthorType.LOGIN_CUSTOMER) || isAuthorType(classTypes, AuthorType.LOGIN_CUSTOMER)) && !isAuthorType(methodTypes, AuthorType.LOGIN_CUSTOMER_NOT)){
			String cookie_value = CookieUtil.getCookie(request, cookie_name);
			
			if(!StringUtils.isEmpty(cookie_value)){
				LoginContext context = LoginContextEncrypt.decodeContext(cookie_value);
				CookieUtil.addCookie(response, cookie_name, LoginContextEncrypt.encodingContext(context));
				authorLocal.getLocal().set(context);
			}else{
				response.sendRedirect(base_url+"cus/center");
				return false;
			}
			
		}
		//检查用户是否登陆
		if((isAuthorType(methodTypes, AuthorType.LOGIN_USER) || isAuthorType(classTypes, AuthorType.LOGIN_USER)) && !isAuthorType(methodTypes, AuthorType.LOGIN_USER_NOT)){
			
			String cookie_value = CookieUtil.getCookie(request, cookie_name);
			
			if(StringUtils.isEmpty(cookie_value)){
				response.sendRedirect(login_url);
				return false;
			}
			
			LoginContext context = LoginContextEncrypt.decodeContext(cookie_value);
			//检查登陆是否过期
			if(context.isTimeout()){
				response.sendRedirect(login_url);
				return false;
			}
			
			//重新设置cookie
			context.updateLoginTime();
			
			CookieUtil.addCookie(response, cookie_name, LoginContextEncrypt.encodingContext(context));
			
			authorLocal.getLocal().set(context);
			
		}
		return true;
	}
	
	/**
	 * 后处理拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
//		try {
//			Map<String,Object> models = modelAndView.getModel();
//			if(null != models){
//				Object bascUrl = models.get(Constants.BASE_URL_NAME);
//				Object bascStaticUrl = models.get(Constants.BASE_STATIC_URL_NAME);
//				
//				if(null != bascUrl){
//					models.put(Constants.BASE_URL_NAME, bascUrl.toString().replaceAll(Constants.DOMAIN_PROFIX, host.get()));
//				}
//				if(null != bascStaticUrl){
//					models.put(Constants.BASE_STATIC_URL_NAME, bascStaticUrl.toString().replaceAll(Constants.DOMAIN_PROFIX, host.get()));
//				}
//			}
//		} catch (Exception e) {
//		}
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
		//移除线程空间
		timer.remove();
		authorLocal.getLocal().remove();
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
