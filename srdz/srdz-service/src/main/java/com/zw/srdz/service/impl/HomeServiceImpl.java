package com.zw.srdz.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zw.srdz.dao.ProductDao;
import com.zw.srdz.domain.Advertising;
import com.zw.srdz.domain.Group;
import com.zw.srdz.domain.Product;
import com.zw.srdz.domain.Type;
import com.zw.srdz.service.HomeService;
import com.zw.srdz.service.ProductService;
import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;

@Service
public class HomeServiceImpl extends BaseService implements HomeService{

	@Resource private ProductDao productDao;
	@Resource private ProductService productService;
	
	@Value(value="${index.page.size}")
	private int pageSize;
	@Override
	public Map<String, Object> loadGroup(int group, List<Type> types) throws Exception {
		Map<String, Object> data = Maps.newHashMap();
		
		Group gp = CacheManager.getInstance().getGroup(group);
		//获取第一个type对应的首页数据
		Type firstType = types.get(0);
		List<Product> products = null;
		if(Type.ORDER_CLICK == firstType.getOrderType()){
			products = productDao.queryByTypeTimeDesc(firstType.getId(), 0, this.pageSize);
		}else{
			products = productDao.queryByTypeTimeDesc(firstType.getId(), 0, this.pageSize);
		}
		
		//判断显示方式, 如果是图标显示则转换成 json，否则不做任何转换
		if(Type.DISPLAY_ICON == firstType.getDisplayType()){
			data.put("json", JSONObject.toJSONString(products));
			data.put("display", "icon");
		}else{
			data.put("advertising", CacheManager.getInstance().getAdvertising(Advertising.LOCATION_PRODUCT_LIST));//获取一个随机的列表广告
			data.put("advertisingIndex", new Random().nextInt(products.size()));
			data.put("display", "list");
		}
		data.put("products", null == products ? Lists.newArrayList() : products);
		data.put("types", types);
		data.put("group", gp);
		return data;
	}
	
	@Override
	public Map<String, Object> loadList(int type, int display, int order,int start) throws Exception {
		
		Map<String, Object> data = Maps.newHashMap();
		List<Product> products = null;
		if(order == Type.ORDER_CLICK){
			products = productDao.queryByTypeClickDesc(type, start, this.pageSize);
		}else{
			products = productDao.queryByTypeTimeDesc(type, start, this.pageSize);
		}
		if(Type.DISPLAY_LIST == display){
			data.put("advertising", CacheManager.getInstance().getAdvertising(Advertising.LOCATION_PRODUCT_LIST));//获取一个随机的列表广告
		}
		if(products.isEmpty()){
			data.put("isFinish", "true");
		}
		data.put("advertisingIndex", new Random().nextInt(products.size()));
		data.put("products", products);
		return data;
	}
	
	@Override
	public void clickProduct(int id) {
		productService.addClick(id);
	}
	
	@Override
	public void pariseProduct(int id) {
		productService.addParise(id);
	}
}
