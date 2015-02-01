package com.zw.srdz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zw.srdz.dao.ProductDao;
import com.zw.srdz.domain.Group;
import com.zw.srdz.domain.Page;
import com.zw.srdz.domain.Product;
import com.zw.srdz.domain.ProductTypeAttr;
import com.zw.srdz.domain.Type;
import com.zw.srdz.service.ProductService;
import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;
import com.zw.srdz.service.task.ClickProductTask;
import com.zw.srdz.service.task.PraiseProductTask;
import com.zw.srdz.service.task.TaskManager;

@Service
public class ProductServiceImpl extends BaseService implements ProductService{

	private Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Resource private ProductDao productDao;
	@Resource private TaskManager taskManager;
	
	@Override
	public Page listProduct(int type, Page page) throws Exception {
		
		int count = productDao.getProductCountByType(type);
		List<Product> items = productDao.queryByTypeTimeDesc(type, page.getStart(), page.getPageSize());
		
		page.setAllCount(count);
		page.setItems(items);
		
		return page;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean addProduct(Product product, String types) throws Exception {
		
		product.setCreateTime(new Date());
		product.setClickCount(0);
		product.setPraiseCount(0);
		
		productDao.addProduct(product);
		
		//添加商品对应的所有属性信息
		productDao.batchAddProductTypeAttr(this.convert(product.getId(), types));
		
		return true;
				
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean updateProduct(Product product, String types)throws Exception {
		
		product.setUpdateTime(new Date());
		
		//修改商品信息
		productDao.updateProduct(product);
		//删除商品对应的所有属性信息
		productDao.deleteProductTypeAttr(product.getId());
		//重新添加商品对应的所有属性信息
		productDao.batchAddProductTypeAttr(this.convert(product.getId(), types));
		
		return true;
	}

	@Override
	public Product getProduct(int id) throws Exception {
		
		return productDao.getProduct(id);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean removeProduct(int id) throws Exception {
		
		productDao.deleteProduct(id);
		productDao.deleteProductTypeAttr(id);
		
		return true;
	}

	@Override
	public boolean removeProduct(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)){
			return true;
		}
		String [] arr = ids.split(",");
		for(String id : arr){
			try {
				this.removeProduct(Integer.parseInt(id));
			} catch (Exception e) {
				log.error("删除商品:["+id+"]失败.",e);
			}
		}
		return true;
	}
	
	@Override
	public void addClick(int id) throws Exception {
		
		ClickProductTask task = new ClickProductTask(productDao, id);
		boolean flag = taskManager.addTask(task);
		if(!flag){
			log.warn("添加纪录商品点击数的task 失败");
		}
	}

	@Override
	public void addParise(int id) throws Exception {
		PraiseProductTask task = new PraiseProductTask(productDao, id);
		boolean flag = taskManager.addTask(task);
		if(!flag){
			log.warn("添加纪录商品点赞数的task 失败");
		}
	}

	@Override
	public Map<String, Object> loadNew() throws Exception {
		
		Map<String, Object> data = Maps.newHashMap();
		
		List<Group> groups = CacheManager.getInstance().getGroups();
		for(Group group: groups){
			group.setTypes(CacheManager.getInstance().getTypes(group.getId()));
		}
		
		data.put("groups", groups);
		return data;
	}
	
	@Override
	public Map<String, Object> loadUpdate(int id) throws Exception {
		Map<String, Object> data = Maps.newHashMap();
		
		//从缓存中加载分类数据
		List<Group> groups = CacheManager.getInstance().getGroups();
		//加载商品对应的分类属性
		List<ProductTypeAttr> attrs = productDao.queryProductTypeAttr(id);
		Map<Object, String> att = Maps.newHashMap();
		for(Group group: groups){
			List<Type> types = CacheManager.getInstance().getTypes(group.getId());
			for(Type type: types){
				if(type.isChecked(attrs)){
					att.put(type.getId(), "true");
				}
			}
			group.setTypes(types);
		}
		//获取商品数据
		Product product = productDao.getProduct(id);
		
		data.put("att", att);
		data.put("product", product);
		data.put("groups", groups);
		return data;
	}
	
	/**
	 * 将字符串转换成属性信息集合
	 * @param types
	 * @return
	 * @throws Exception
	 */
	private List<ProductTypeAttr> convert(int productId,String types) throws Exception{
		if(StringUtils.isEmpty(types)){
			return Lists.newArrayList();
		}
		String [] arr = types.split(",");
		List<ProductTypeAttr> attrs = Lists.newArrayList();
		
		for(String type:arr){
			ProductTypeAttr attr = new ProductTypeAttr();
			attr.setProductId(productId);
			attr.setTypeId(Integer.parseInt(type));
			
			attrs.add(attr);
		}
		
		return attrs;
	}
}
