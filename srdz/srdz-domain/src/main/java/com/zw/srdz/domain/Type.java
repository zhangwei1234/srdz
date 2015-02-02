package com.zw.srdz.domain;

import java.util.Date;
import java.util.List;

/**
* 项目名称：srdz-domain   
* 类名称：Type   
* 类描述：   分类信息
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-29 下午1:23:20   
* 修改人：zhangwei
* 修改时间：2015-1-29 下午1:23:20   
* 修改备注：   
* @version    
*
 */
public class Type {

	public static final int DISPLAY_ICON = 1;//图标的方式展现该分类下商品信息
	public static final int DISPLAY_LIST = 2;//列表的方式展现该分类下商品信息
	public static final int ORDER_TIME = 1;//按照商品创建时间对商品进行倒序展现
	public static final int ORDER_CLICK = 2;//按照商品点击量对商品进行倒序展现
	
	private int id;
	private String name;
	private String remark;
	private int groupId;
	private int orderNo;
	private int displayType;
	private int orderType;
	private String iconUrl;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
	
	public int getDisplayType() {
		return displayType;
	}
	
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}
	
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getOrderType() {
		return orderType;
	}
	
	public boolean isChecked(List<ProductTypeAttr> attrs){
		if(null != attrs){
			for(ProductTypeAttr attr: attrs){
				if(attr.getTypeId() == this.id){
					return true;
				}
			}
		}
		return false;
	}
}
