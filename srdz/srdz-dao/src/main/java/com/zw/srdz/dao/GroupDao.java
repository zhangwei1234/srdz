package com.zw.srdz.dao;

import java.util.List;

import com.zw.srdz.domain.Group;

public interface GroupDao {

	/**
	 * 新增一个分组
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public boolean addGroup(Group group) throws Exception;
	
	/**
	 * 获取一个分组
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Group getGroup(int id) throws Exception;
	
	/**
	 * 获取所有分组信息
	 * @return
	 * @throws Exception
	 */
	public List<Group> queryGroups() throws Exception;
	
	/**
	 * 删除一个分组信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteGroup(int id) throws Exception;
	
	/**
	 * 修改一个分组信息
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public boolean updateGroup(Group group) throws Exception;
}
