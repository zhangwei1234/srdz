package com.zw.srdz.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.zw.srdz.author.Author;
import com.zw.srdz.author.AuthorType;
import com.zw.srdz.base.BaseController;
import com.zw.srdz.common.util.CookieUtil;
import com.zw.srdz.domain.User;
import com.zw.srdz.domain.author.LoginContext;
import com.zw.srdz.domain.author.LoginContextEncrypt;
import com.zw.srdz.service.CustomerService;
import com.zw.srdz.service.UserService;

/**
* 项目名称：srdz-web   
* 类名称：CustomerController   
* 类描述：   客户中心
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-2-11 下午1:47:01   
* 修改人：zhangwei
* 修改时间：2015-2-11 下午1:47:01   
* 修改备注：   
* @version    
*
 */

@Controller
@RequestMapping(value="/cus")
@Author(type={AuthorType.LOGIN_CUSTOMER})
public class CustomerController extends BaseController{

	@Value(value="${cookie.name}")
	private String cookie_name;
	
	@Resource
	private CustomerService customerService;
	
	@Resource 
	private UserService userService;
	
	@Author(type={AuthorType.LOGIN_CUSTOMER_NOT, AuthorType.LOGIN_CUSTOMER_COOKIE})
	@RequestMapping(value="/center", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView center(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return toVmIndex("customer/customer", customerService.center());
	}
	
	@Author(type={AuthorType.LOGIN_CUSTOMER_NOT})
	@RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return toVmIndex("customer/login", null);
	}
	
	@Author(type={AuthorType.LOGIN_CUSTOMER_NOT})
	@RequestMapping(value="/dologin", method={RequestMethod.POST})
	public ModelAndView doLogin(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		String account = req.getParameter("account");
		String passwd  = req.getParameter("passwd");
		
		Map<String, Object> data = customerService.doLogin(account, passwd);
		
		boolean status = (Boolean)data.get("status");
		
		if(status){//登陆成功
			CookieUtil.addCookie(res, cookie_name, LoginContextEncrypt.encodingContext(new LoginContext((User)data.get("customer"))));
			res.sendRedirect(base_url+"cus/center");
			return null;
		}else{//登陆失败
			return toVmIndex("customer/login", data);
		}
	}
	
	@Author(type={AuthorType.LOGIN_CUSTOMER_NOT})
	@RequestMapping(value="/registry", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView registry(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return toVmIndex("customer/registry", null); 
	}
	
	@Author(type={AuthorType.LOGIN_CUSTOMER_NOT})
	@RequestMapping(value="/doRegistry", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView doRegistry(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String account = req.getParameter("account");
		String pwd     = req.getParameter("passwd");
		
		Map<String, Object> data = customerService.doRegistry(account, pwd);
		boolean flag = (Boolean)data.get("status");
		if(flag){
			CookieUtil.addCookie(res, cookie_name, LoginContextEncrypt.encodingContext(new LoginContext((User)data.get("user"))));
			res.sendRedirect(base_url+"cus/center");
			return null;
		}
		return toVmIndex("customer/registry", data);
	}
	
	@RequestMapping(value="/logout", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView logOut(HttpServletRequest req, HttpServletResponse res) throws Exception{
		//移除cookie信息
		CookieUtil.delCookie(res, null, cookie_name);
		
		//返回控制台首页
		res.sendRedirect(base_url+"cus/center");
		return null;
	}
	
	@RequestMapping(value="/info",method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView loadUpdateCustomer(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		User user = userService.getCurrentUser();
		Map<String, Object> data = Maps.newHashMap();
		data.put("customer", user);
		
		return toVmIndex("customer/customer_info", data);
	}
	
	@RequestMapping(value="/doUpdateCustomer", method={RequestMethod.POST})
	public ModelAndView doUpdateCustomer(HttpServletRequest req, HttpServletResponse res, @ModelAttribute User user) throws Exception{
		
		boolean flag = userService.update(user);
		if(flag){
			res.sendRedirect(base_url+"cus/center");
			return null;
		}
		res.sendRedirect(base_url+"cus/info");
		return null;
	}
}
