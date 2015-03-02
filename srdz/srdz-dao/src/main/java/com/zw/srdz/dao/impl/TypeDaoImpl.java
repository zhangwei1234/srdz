package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.zw.srdz.dao.TypeDao;
import com.zw.srdz.domain.Type;
import com.zw.srdz.dao.base.BaseDao;

@Repository("typeDao")
public class TypeDaoImpl extends BaseDao implements TypeDao{

	private final String base = "com.zw.srdz.dao.TypeDao.";
	
	@Override
	public Type getType(int id) throws Exception {
		return template.selectOne(getNameSpace(this.base, "QUERY_BY_ID"), id);
	}

	@Override
	public boolean addType(Type type) throws Exception {
		
		type.setCreateTime(new Date());
		return template.insert(getNameSpace(this.base, "ADD_TYPE"), type) > 0;
	}

	@Override
	public List<Type> queryTypes() throws Exception {
		return template.selectList(getNameSpace(this.base, "QUERY_TYPES"));
	}

	@Override
	public List<Type> queryTypesByGroup(int group) throws Exception {
		
		return template.selectList(getNameSpace(this.base, "QUERY_TYPES_BY_GROUP"), group);
	}

	@Override
	public boolean deleteType(int id) throws Exception {
		return template.delete(getNameSpace(this.base, "DELETE_BY_ID"), id) > 0;
	}

	@Override
	public boolean updateType(Type type) throws Exception {
		type.setUpdateTime(new Date());
		return template.update(getNameSpace(this.base, "UPDATE_TYPE"), type) > 0;
	}

	@Override
	public boolean existsTypeByGroup(int group) throws Exception {
		Object obj = template.selectOne(getNameSpace(this.base, "EXISTS_TYPE_BY_GROUP"), group);
		if (null == obj){
			return false;
		}
		return (Integer)obj >0;
	}

}
