package com.zw.srdz.domain.author;

import com.zw.srdz.domain.User;

/**
 * 当前用户上下文信息
 * 
 * @author cdzhangwei3
 *
 */
public class LoginContext {

	private String name;
	private String account;
	private String mobilePhone;
	private String sex;
	private String admin;
	private long loginTime;
	public LoginContext(){}
	
	public LoginContext(User user){
		this.name = user.getName();
		this.account = user.getAccount();
		this.mobilePhone = user.getMobilePhone();
		this.sex = user.convSex();
		this.admin = Boolean.toString(user.isManager());
		this.loginTime = System.currentTimeMillis();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	public long getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	
	/**
	 * 判断登陆是否超时
	 * @return
	 * @throws Exception
	 */
	public boolean isTimeout()throws Exception{
		long now = System.currentTimeMillis();
		long timeOut = 30 * 60 * 1000 ;
		if ((now - this.loginTime) > timeOut){
			return true;
		}
		return false;
	}
	
	/**
	 * 修改操作时间
	 */
	public void updateLoginTime(){
		this.loginTime = System.currentTimeMillis();
	}
	
	/**
	 * 判断是否是管理员
	 * @return
	 */
	public boolean isAdmin(){
		return "true".equals(this.admin);
	}
}
