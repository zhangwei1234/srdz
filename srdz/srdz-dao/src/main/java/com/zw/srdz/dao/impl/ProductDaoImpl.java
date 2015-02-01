package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.zw.srdz.dao.ProductDao;
import com.zw.srdz.dao.base.BaseDao;
import com.zw.srdz.domain.Product;
import com.zw.srdz.domain.ProductTypeAttr;

@Repository("productDao")
public class ProductDaoImpl extends BaseDao implements ProductDao{

	private final String base = "com.zw.srdz.dao.ProductDao.";
	
	@Override
	public List<Product> queryByTypeTimeDesc(int type, int start, int length)throws Exception {
		Map<String, Object> param = Maps.newHashMap();
		param.put("type", type);
		param.put("start", start);
		param.put("length", length);
		
		return template.selectList(getNameSpace("QUERY_BY_TYPE_DESC_TIME"), param);
	}


	@Override
	public List<Product> queryByTypeClickDesc(int type, int start, int length)throws Exception {
		
		Map<String, Object> param = Maps.newHashMap();
		param.put("type", type);
		param.put("start", start);
		param.put("length", length);
		
		return template.selectList(getNameSpace("QUERY_BY_TYPE_DESC_CLICK"), param);
		
	}


	@Override
	public List<Product> queryProduct(int start, int length) throws Exception {
		Map<String, Object> param = Maps.newHashMap();
		param.put("start", start);
		param.put("length", length);
		
		return template.selectList(getNameSpace("QUERY_PRODUCT"), param);
	}


	@Override
	public int getProductCountByType(int type) throws Exception {
		
		return (Integer) template.selectOne(getNameSpace("QUERY_COUNT_BY_TYPE"), type);
	}


	@Override
	public int getProductCount() throws Exception {
		
		return (Integer) template.selectOne(getNameSpace("QUERY_COUNT"));
	}


	@Override
	public Product getProduct(int id) throws Exception {
		
		return template.selectOne(getNameSpace("QUERY_BY_ID"), id);
	}


	@Override
	public boolean addProduct(Product product) throws Exception {
		
		product.setCreateTime(new Date());
		return template.update(getNameSpace("ADD_PRODUCT"), product) > 0;
	}


	@Override
	public boolean updateProduct(Product product) throws Exception {
		product.setUpdateTime(new Date());
		return template.update(getNameSpace("UPDATE_PRODUCT"), product) >0;
	}
	
	@Override
	public boolean addProductClick(int id) throws Exception {
		
		return template.update(getNameSpace("ADD_PRODUCT_CLICK"), id) >0;
	}


	@Override
	public boolean addProductPraise(int id) throws Exception {
		
		return template.update(getNameSpace("ADD_PRODUCT_PRAISE"), id) >0;
	}


	@Override
	public boolean deleteProduct(int id) throws Exception {
		
		return template.delete(getNameSpace("DELETE_PRODUCT"), id) > 0;
	}


	@Override
	public boolean deleteProductTypeAttr(int id) throws Exception {
		
		return template.delete(getNameSpace("DELETE_PRODUCT_TYPE_ATTR"), id) > 0;
	}


	@Override
	public List<ProductTypeAttr> queryProductTypeAttr(int id) throws Exception {
		
		return template.selectList(getNameSpace("QUERY_PRODUCT_TYPE_ATTR"), id);
	}


	@Override
	public boolean batchAddProductTypeAttr(List<ProductTypeAttr> attrs)throws Exception {
		
		return template.insert(getNameSpace("BATCH_ADD_PRODUCT_TYPE_ATTR"), attrs) > 0;
	}


	@Override
	protected String getNameSpace(String space) {
		return base + space;
	}
}
