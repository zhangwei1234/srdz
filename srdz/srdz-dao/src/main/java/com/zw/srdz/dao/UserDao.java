package com.zw.srdz.dao;

import java.util.List;

import com.zw.srdz.domain.User;

public interface UserDao {

	/**
	 * 查询用户信息
	 * @param account 登陆账号/手机号/邮箱账号
	 * @return
	 * @throws Exception
	 */
	public User getUserLike(String account) throws Exception;
	/**
	 * 插入用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUser(User user) throws Exception;
	
	/**
	 * 根据账号获取用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public User getUser(String account) throws Exception;
	
	/**
	 * 查询所有用户信息
	 * @return
	 * @throws Exception
	 */
	public List<User> queryUsers() throws Exception ;
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateUser(User user) throws Exception;
}
