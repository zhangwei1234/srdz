package com.zw.srdz.service;

import java.util.List;

import com.zw.srdz.domain.User;
import com.zw.srdz.domain.author.LoginContext;

public interface UserService {

	/**
	 * 用户登陆
	 * @param account
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public LoginContext login(String account, String passwd) throws Exception;
	
	/**
	 * 加载所有用户信息
	 * @return
	 * @throws Exception
	 */
	public List<User> loadUsers() throws Exception;
	
	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUser(User user) throws Exception;
	
	/**
	 * 根据用户账号获取用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public User getUser(String account) throws Exception;
	
	/**
	 * 获取当前登录用户信息
	 * @return
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception;
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean update(User user) throws Exception;
}
