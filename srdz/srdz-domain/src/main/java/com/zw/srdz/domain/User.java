package com.zw.srdz.domain;

import java.util.Date;

/**
* 项目名称：srdz-domain   
* 类名称：User   
* 类描述：   用户基本信息
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-29 下午1:22:50   
* 修改人：zhangwei
* 修改时间：2015-1-29 下午1:22:50   
* 修改备注：   
* @version    
*
 */
public class User {

	public final static int TYPE_MANAGER = 1;//管理类账号
	public final static int TYPE_CUSTOMER = 2;//用户类账号
	
	private int id;
	private String name;
	private String account;
	private String passwd;
	private int sex;
	private int type;
	private String email;
	private String mobilePhone;
	private Date createTime;
	private Date updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public boolean isManager(){
		return TYPE_MANAGER == this.type;
	}
	
	public String convSex(){
		return this.sex == 1? "男" :"女";
	}
}
