package com.zw.srdz.domain.author;

/**
 * 封装当前登陆的用户状态信息,保证多线程安全
 * 
 * @author cdzhangwei3
 *
 */
public class AuthorContext {

	private final ThreadLocal<LoginContext> loginContextLocal = new ThreadLocal<LoginContext>();
	
	public ThreadLocal<LoginContext> getLocal(){
		return loginContextLocal;
	}
}
