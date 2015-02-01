package com.zw.srdz.service;

import java.util.List;
import java.util.Map;

import com.zw.srdz.domain.Type;

public interface TypeService {

	/**
	 * 添加一个商品分类信息
	 * @param type
	 * @throws Exception
	 */
	public boolean addType(Type type) throws Exception;
	
	/**
	 * 删除一个商品分类信息
	 * @param typeId
	 * @throws Exception
	 */
	public boolean removeType(int typeId) throws Exception;
	
	/**
	 * 获取一个商品分类信息
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public Type getType(int typeId) throws Exception;
	
	/**
	 * 修改一个商品分类信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean updateType(Type type) throws Exception;
	
	/**
	 * 对商品分类进行升序或降序
	 * @param ascId 升
	 * @param descIt 降
	 * @return
	 * @throws Exception
	 */
	public boolean ascTdescType(int ascId, int descIt) throws Exception;
	
	/**
	 * 获取所有分类信息
	 * @return
	 * @throws Exception
	 */
	public List<Type> queryTypes() throws Exception;
	
	/**
	 * 获取分组下面的所有分类信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List<Type> queryTypesByGroup(int groupId) throws Exception;
	
	/**
	 * 首页数据加载
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loadIndex(int group) throws Exception;
	
	/**
	 * 加载修改界面
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loadUpdate(int typeId) throws Exception;
}
