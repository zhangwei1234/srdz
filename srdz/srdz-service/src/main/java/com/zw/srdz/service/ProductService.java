package com.zw.srdz.service;

import java.util.Map;

import com.zw.srdz.domain.Page;
import com.zw.srdz.domain.Product;

public interface ProductService {

	/**
	 * 查询分类下面的所有商品信息,分页
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page listProduct(int type , Page page) throws Exception;
	
	/**
	 * 添加一个商品信息
	 * @param product
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public boolean addProduct(Product product,String types) throws Exception;
	
	/**
	 * 修改一个商品信息
	 * @param product
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public boolean updateProduct(Product product, String types) throws Exception;
	
	/**
	 * 获取一个商品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Product getProduct(int id) throws Exception;
	
	/**
	 * 删除一个商品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean removeProduct(int id) throws Exception;
	
	/**
	 * 批量删除商品信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean removeProduct(String ids) throws Exception;
	
	/**
	 * 记录商品的一次点击数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void addClick(int id) throws Exception;
	
	/**
	 * 记录商品的一次点赞数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void addParise(int id) throws Exception;
	
	/**
	 * 加载新增产品所需的数据信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loadNew() throws Exception;
	
	/**
	 * 加载修改界面所需的数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loadUpdate(int id) throws Exception;
}
