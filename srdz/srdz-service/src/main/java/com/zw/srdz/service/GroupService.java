package com.zw.srdz.service;

import java.util.List;

import com.zw.srdz.domain.Group;

public interface GroupService {

	/**
	 * 获取分组信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public Group getGroup(String groupId) throws Exception;
	
	/**
	 * 获取所有分组信息
	 * @return
	 * @throws Exception
	 */
	public List<Group> listGroups() throws Exception;
	
	/**
	 * 添加分组信息
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public boolean addGroup(Group group) throws Exception;
	
	/**
	 * 修改分组信息
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public boolean updateGroup(Group group) throws Exception;
	
	/**
	 * 删除分组信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public boolean removeGroup(String groupId) throws Exception;
	
	/**
	 * 升序&降序，每次只能升降一个名次
	 * @param ascId 升
	 * @param descId 降
	 * @return
	 * @throws Exception
	 */
	public boolean ascTDesc(String ascId,String descId) throws Exception;
}
