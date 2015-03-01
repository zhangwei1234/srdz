package com.zw.srdz.service.impl;

import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.zw.srdz.common.util.Encrypt;
import com.zw.srdz.dao.UserDao;
import com.zw.srdz.domain.User;
import com.zw.srdz.domain.author.LoginContext;
import com.zw.srdz.service.CustomerService;
import com.zw.srdz.service.base.BaseService;

@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

	@Resource
	private UserDao userDao;
	
	@Override
	public Map<String, Object> center() throws Exception {
		
		Map<String, Object> data = Maps.newHashMap();
		LoginContext loginContext = getLoginContext();
		if(null == loginContext){//用户未登陆
			data.put("isLogin", "false");
		}else{//已经登陆
			User user = userDao.getUser(getAccount());
			data.put("isLogin", "true");
			data.put("customer", user);
		}
		
		return data;
	}
	
	@Override
	public Map<String, Object> doLogin(String account, String passwd)throws Exception {
		Map<String, Object> data = Maps.newHashMap();
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(passwd)){
			data.put("status", false);
			data.put("msg", "请输入用户名或密码");
			return data;
		}
		//获取用户账号
		User user = userDao.getUserLike(account);
		if(null == user){
			data.put("status", false);
			data.put("msg", "账号不存在,新用户请先注册");
		}else{
			//匹配密码是否正确
			passwd = Encrypt.encodemd5(passwd);
			if (passwd.equals(user.getPasswd())){
				data.put("status", true);
				data.put("context", new LoginContext(user));
				data.put("isLogin", "true");
				data.put("customer", user);
			}else{
				data.put("status", false);
				data.put("msg", "账号或密码输入错误");
			}
		}
		return data;
	}
	
	@Override
	public Map<String, Object> doRegistry(String account, String pwd)throws Exception {
		//检查登陆账号是否合法
		Map<String, Object> data = Maps.newHashMap();
		if(StringUtils.isEmpty(account) || account.length()<4 || account.length()>20 
				|| !Pattern.compile("^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$").matcher(account).find()){
			data.put("status", false);
			data.put("msg", "账号不合法(4-20位字符,支持数字/字母/-/_组合)");
			return data;
		}
		
		//检查账号是否存在
		User user = userDao.getUser(account);
		if(null != user){
			data.put("status", false);
			data.put("msg", "账号已经存在,请重新输入");
			return data;
		}
		
		//插入用户信息
		User newUser = new User();
		newUser.setAccount(account);
		newUser.setPasswd(Encrypt.encodemd5(pwd));
		newUser.setType(User.TYPE_CUSTOMER);
		
		boolean flag = userDao.addUser(newUser);
		
		if(!flag){//用户注册失败
			data.put("status", false);
			data.put("msg", "系统繁忙,请稍后重试");
		}else{
			data.put("status", true);
			data.put("user", newUser);
		}
		
		return data;
	}
}
