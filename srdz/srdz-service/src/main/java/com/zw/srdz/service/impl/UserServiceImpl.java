package com.zw.srdz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zw.srdz.common.util.Encrypt;
import com.zw.srdz.dao.UserDao;
import com.zw.srdz.domain.User;
import com.zw.srdz.domain.author.LoginContext;
import com.zw.srdz.service.UserService;
import com.zw.srdz.service.base.BaseService;

/**
* 项目名称：srdz-service   
* 类名称：UserServiceImpl   
* 类描述：   用户信息服务类
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-30 上午11:08:19   
* 修改人：zhangwei
* 修改时间：2015-1-30 上午11:08:19   
* 修改备注：   
* @version    
*
 */
@Service
public class UserServiceImpl extends BaseService implements UserService{

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Resource private UserDao userDao;
	
	@Override
	public LoginContext login(String account, String passwd) throws Exception {
		
		User user = userDao.getUser(account);
		if (null == user){
			return null;
		}
		
		//匹配密码是否正确
		passwd = Encrypt.encodemd5(passwd);
		if (passwd.equals(user.getPasswd())){
			return new LoginContext(user);
		}
		return null;
	}
	
	@Override
	public List<User> loadUsers() throws Exception {
		return userDao.queryUsers();
	}
	
	@Override
	public boolean addUser(User user) throws Exception {
		user.setPasswd(Encrypt.encodemd5(user.getPasswd()));
		return userDao.addUser(user);
	}
	
	@Override
	public User getUser(String account) throws Exception {
		return userDao.getUser(account);
	}
	
	@Override
	public User getCurrentUser() throws Exception {
		return userDao.getUser(getAccount());
	}
	
	@Override
	public boolean update(User user) throws Exception {
		return userDao.updateUser(user);
	}
}
