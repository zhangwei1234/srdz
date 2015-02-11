package com.zw.srdz.service.base;

import javax.annotation.Resource;

import com.zw.srdz.domain.author.AuthorContext;
import com.zw.srdz.domain.author.LoginContext;

public class BaseService {

	@Resource AuthorContext authorLocal;
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	protected LoginContext getLoginContext(){
		return authorLocal.getLocal().get();
	}
	
	/**
	 * 获取当前登录用户账号
	 * @return
	 */
	protected String getAccount(){
		return getLoginContext().getAccount();
	}
}
