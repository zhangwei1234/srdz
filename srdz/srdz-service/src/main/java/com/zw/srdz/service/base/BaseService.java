package com.zw.srdz.service.base;

import javax.annotation.Resource;

import com.zw.srdz.domain.author.AuthorContext;
import com.zw.srdz.domain.author.LoginContext;

public class BaseService {

	@Resource AuthorContext authorLocal;
	
	protected LoginContext getLoginContext(){
		return authorLocal.getLocal().get();
	}
	
}
