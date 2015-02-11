package com.zw.srdz.author;

/**
 * 认证类型
 * 
 * @author zhangwei
 *
 */
public enum AuthorType {

	/**
	 * 用户登录验证
	 */
	LOGIN_USER(1),
	
	/**
	 * 不需要登录
	 */
	LOGIN_USER_NOT(2),
	
	/**
	 * 客户登录验证
	 */
	LOGIN_CUSTOMER(3);
	
	private int val;
	AuthorType(int val){
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
	
}
