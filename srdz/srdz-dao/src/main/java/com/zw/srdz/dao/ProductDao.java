package com.zw.srdz.dao;

import java.util.List;

import com.zw.srdz.domain.Product;
import com.zw.srdz.domain.ProductTypeAttr;

public interface ProductDao {

	/**
	 * 根据分类查询商品信息,支持分页,按照创建时间 降序
	 * @param type
	 * @param start
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryByTypeTimeDesc(int type,int start,int length) throws Exception;
	
	/**
	 * 根据分类查询商品信息，支持分页,按照点击数降序
	 * @param type
	 * @param start
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryByTypeClickDesc(int type, int start, int length) throws Exception;
	
	/**
	 * 查询所有商品信息,分页,按照创建时间降序
	 * @param start
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryProduct(int start, int length) throws Exception;
	
	/**
	 * 获取分类下面商品数量信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int getProductCountByType(int type) throws Exception;
	
	/**
	 * 获取所有商品数量
	 * @return
	 * @throws Exception
	 */
	public int getProductCount() throws Exception;
	
	/**
	 * 获取商品详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Product getProduct(int id) throws Exception;
	
	/**
	 * 添加一个商品信息
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public boolean addProduct(Product product) throws Exception;
	
	/**
	 * 修改商品信息
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public boolean updateProduct(Product product) throws Exception;
	
	/**
	 * 根据商品ID增加商品的一次点击量
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean addProductClick(int id) throws Exception;
	
	/**
	 * 增加商品的一次点赞数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean addProductPraise(int id) throws Exception;
	
	/**
	 * 删除一个商品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteProduct(int id) throws Exception;
	
	/**
	 * 删除商品对应的所有类目属性
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteProductTypeAttr(int id) throws Exception;
	
	/**
	 * 查询一个商品对应的所有类目属性
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ProductTypeAttr> queryProductTypeAttr(int id) throws Exception;
	
	/**
	 * 批量添加商品的类目属性
	 * @param attrs
	 * @return
	 * @throws Exception
	 */
	public boolean batchAddProductTypeAttr(List<ProductTypeAttr> attrs) throws Exception;
}
