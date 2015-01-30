package com.zw.srdz.controller.console;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
import com.zw.srdz.service.UserService;

/**
* 项目名称：srdz-web   
* 类名称：UserController   
* 类描述：   用户控制,负责用户登陆/操作等控制
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-30 上午10:13:00   
* 修改人：zhangwei
* 修改时间：2015-1-30 上午10:13:00   
* 修改备注：   
* @version    
*
 */
@Controller
@RequestMapping(value="console/user")
@Author(type={AuthorType.LOGIN_USER})
public class UserController extends BaseController{

	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Value(value="${login_url}")
	private String login_url;
	@Value(value="${console_url}")
	private String console_url;
	@Value(value="${cookie.name}")
	private String cookie_name;
	
	@Resource private UserService userService;
	/**
	 * 加载登陆界面
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@Author(type={AuthorType.LOGIN_USER_NOT})
	@RequestMapping(value="/login", method={RequestMethod.GET})
	public ModelAndView loadLogin(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return toVm("console/user/user_login", null);
	}
	
	@Author(type={AuthorType.LOGIN_USER_NOT})
	@RequestMapping(value="/doLogin", method={RequestMethod.POST})
	public ModelAndView doLogin(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		String account = req.getParameter("username");
		String passwd  = req.getParameter("password");
		
		LoginContext context = userService.login(account, passwd);
		if(null == context){//登陆失败
			log.warn("用户:{"+account+"} 登陆失败,用户名或密码错误");
			res.sendRedirect(login_url);
			return null;
		}
		
		//判断是否是后台管理员
		if(!context.isAdmin()){
			log.warn("用户:{"+account+"} 登陆失败,非管理类用户不能登陆后台");
			res.sendRedirect(login_url);
			return null;
		}
		log.info("用户:{"+account+"} 登陆成功");
		CookieUtil.addCookie(res, cookie_name, LoginContextEncrypt.encodingContext(context));
		res.sendRedirect(console_url);
		return null;
	}
	
	/**
	 * 退出登陆
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/logout", method={RequestMethod.GET})
	public ModelAndView logOut(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		//移除cookie信息
		CookieUtil.delCookie(res, null, cookie_name);
		
		//返回控制台首页
		res.sendRedirect(console_url);
		return null;
	}
	
	/**
	 * 加载用户管理主界面
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/loadUser", method={RequestMethod.POST})
	public ModelAndView loadUser(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		List<User> users = userService.loadUsers();
		Map<String, Object> data = Maps.newHashMap();
		data.put("users", users);
		
		return toVm("console/user/user", data);
	}
}
