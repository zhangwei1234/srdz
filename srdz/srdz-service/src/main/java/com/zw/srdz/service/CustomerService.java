package com.zw.srdz.service;

import java.util.Map;

/**
 * 客户中心接口
 * @author wei
 *
 */
public interface CustomerService {

	/**
	 * 客户中心
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> center()throws Exception;
	
	/**
	 * 客户登陆
	 * @param account 登陆账号/手机号/邮箱账号
	 * @param passwd 登陆密码
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doLogin(String account, String passwd) throws Exception;
	
	/**
	 * 客户注册
	 * @param account 登陆账号
	 * @param pwd1 密码
	 * @param pwd2 密码2
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doRegistry(String account, String pwd) throws Exception;
}
