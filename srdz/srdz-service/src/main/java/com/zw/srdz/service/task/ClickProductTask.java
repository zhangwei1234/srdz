package com.zw.srdz.service.task;

import com.zw.srdz.dao.ProductDao;

public class ClickProductTask extends DefaultTask{

	private ProductDao productDao;
	private int productId;
	
	public ClickProductTask(ProductDao dao, int productId){
		super("记录商品点击数");
		this.productDao = dao;
		this.productId = productId;
	}
	
	@Override
	public void before() throws Exception {
		
	}

	@Override
	public void after() throws Exception {
		
	}

	@Override
	public boolean execute() throws Exception {
		return this.productDao.addProductClick(this.productId);
	}

	
}
