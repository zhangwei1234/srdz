package com.zw.srdz.dao.base;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public abstract class BaseDao {
	
	@Resource(name="sqlTemplate")
	protected SqlSessionTemplate template;
	
	/**
	 * 获取数据库访问空间
	 * @param space
	 * @return
	 */
	protected abstract String getNameSpace(String space);
}
