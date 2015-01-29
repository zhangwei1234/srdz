package com.zw.srdz.domain;

import java.util.Date;

/**
* 项目名称：srdz-domain   
* 类名称：Product   
* 类描述：   商品信息
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-29 下午1:23:35   
* 修改人：zhangwei
* 修改时间：2015-1-29 下午1:23:35   
* 修改备注：   
* @version    
*
 */
public class Product {

	private int id;
	private String title;
	private String remark;
	private double price;
	private String imageUrl;
	private String productUrl;
	private int clickCount;
	private int praiseCount;
	private Date createTime;
	private Date updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
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
	
}
