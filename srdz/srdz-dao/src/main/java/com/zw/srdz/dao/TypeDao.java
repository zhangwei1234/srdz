package com.zw.srdz.dao;

import java.util.List;

import com.zw.srdz.domain.Type;

public interface TypeDao {

	/**
	 * 获取分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Type getType(int id) throws Exception;
	
	/**
	 * 添加分类信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean addType(Type type) throws Exception;
	
	/**
	 * 获取所有分类信息
	 * @return
	 * @throws Exception
	 */
	public List<Type> queryTypes() throws Exception;
	
	/**
	 * 根据分组获取分类信息
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public List<Type> queryTypesByGroup(int group) throws Exception;
	
	/**
	 * 删除分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteType(int id) throws Exception;
	
	/**
	 * 修改分类信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean updateType(Type type) throws Exception;
	
	/**
	 * 判断分组下是否存在分类信息
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public boolean existsTypeByGroup(int group) throws Exception;
}
