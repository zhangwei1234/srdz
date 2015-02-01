package com.zw.srdz.domain;

import java.util.Date;
import java.util.List;

/**
* 项目名称：srdz-domain   
* 类名称：Group   
* 类描述：   分组信息
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-29 下午1:23:04   
* 修改人：zhangwei
* 修改时间：2015-1-29 下午1:23:04   
* 修改备注：   
* @version    
*
 */
public class Group {

	private int id;
	private String name;
	private String remark;
	private int orderNo;
	private String iconUrl;
	private Date createTime;
	private Date updateTime;
	private List<Type> types;
	
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
	
	public List<Type> getTypes() {
		return types;
	}
	
	public void setTypes(List<Type> types) {
		this.types = types;
	}
	
}
