package com.zw.srdz.service.task;

import com.zw.srdz.dao.ProductDao;

public class PraiseProductTask extends DefaultTask{

	private ProductDao productDao;
	private int productId;
	
	public PraiseProductTask(ProductDao dao, int productId){
		super("记录商品点赞数");
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
		return productDao.addProductPraise(this.productId);
	}

}
