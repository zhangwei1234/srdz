package com.zw.srdz.domain;

import java.util.Date;


/**
* 项目名称：srdz-domain   
* 类名称：Advertising   
* 类描述：   广告
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-2-2 下午3:56:33   
* 修改人：zhangwei
* 修改时间：2015-2-2 下午3:56:33   
* 修改备注：   
* @version    
*
 */
@SuppressWarnings("all")
public class Advertising {

	public static final int LOCATION_HOME = 1;//首页显示
	public static final int LOCATION_PRODUCT_LIST = 2; //商品列表页feed模式
	public static final int DISPLAY_BANNER = 1;//banner 图显示
	public static final int DISPLAY_FEED= 2;// feed模式
	public static final int STATUS_ACTIVE = 1;//激活
	public static final int STATUS_DISABLED = -1; //注销
	
	private int id;
	private String name;
	private String imageUrl;
	private String linkUrl;
	private int location;
	private int display;
	private Date startTime;
	private Date endTime;
	private int status;
	private int createUser;
	private Date createTime;
	
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCreateUser() {
		return createUser;
	}
	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
