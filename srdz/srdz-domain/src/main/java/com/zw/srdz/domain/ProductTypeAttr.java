package com.zw.srdz.domain;

/**
* 项目名称：srdz-domain   
* 类名称：ProductTypeAttr   
* 类描述：   商品的分类属性信息
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-29 下午1:23:49   
* 修改人：zhangwei
* 修改时间：2015-1-29 下午1:23:49   
* 修改备注：   
* @version    
*
 */
public class ProductTypeAttr {

	private int id;
	private int productId;
	private int typeId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
}
