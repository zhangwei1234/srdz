package com.zw.srdz.domain;

import java.util.Date;

/**
 * 用户反馈信息
 * @author wei
 *
 */
public class UserFeedBack {

	private int id;
	private String account;
	private String comment;
	private int status;
	private Date crateTime;
	private String operateUser;
	private Date updateTime;
	private String operateComment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCrateTime() {
		return crateTime;
	}
	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getOperateComment() {
		return operateComment;
	}
	
	public void setOperateComment(String operateComment) {
		this.operateComment = operateComment;
	}
}
