package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zw.srdz.dao.GroupDao;
import com.zw.srdz.dao.base.BaseDao;
import com.zw.srdz.domain.Group;

@Repository("groupDao")
public class GroupDaoImpl extends BaseDao implements GroupDao {

	private final String base = "com.zw.srdz.dao.GroupDao.";
	
	@Override
	public boolean addGroup(Group group) throws Exception {
		group.setCreateTime(new Date());
		return template.insert(getNameSpace(this.base, "ADD_GROUP"), group) > 0;
	}

	@Override
	public Group getGroup(int id) throws Exception {
		return template.selectOne(getNameSpace(this.base, "QUERY_BY_ID"), id);
	}

	@Override
	public List<Group> queryGroups() throws Exception {
		return template.selectList(getNameSpace(this.base, "QUERY_GROUPS"));
	}

	@Override
	public boolean deleteGroup(int id) throws Exception {
		
		return template.delete(getNameSpace(this.base, "DELETE_BY_ID"), id) > 0;
	}

	@Override
	public boolean updateGroup(Group group) throws Exception {
		group.setUpdateTime(new Date());
		return template.update(getNameSpace(this.base, "UPDATE_GROUP"), group) > 0;
	}
	
}
