package com.zw.srdz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.zw.srdz.dao.GroupDao;
import com.zw.srdz.dao.TypeDao;
import com.zw.srdz.domain.Type;
import com.zw.srdz.service.TypeService;
import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;

@Service
public class TypeServiceImpl extends BaseService implements TypeService {

	private Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);
	
	@Resource private TypeDao typeDao;
	@Resource private GroupDao groupDao;
	
	@Override
	public boolean addType(Type type) throws Exception {
		type.setCreateTime(new Date());
		
		boolean flag =  typeDao.addType(type);
		CacheManager.getInstance().initTypes(queryTypes());
		
		return flag;
	}

	@Override
	public boolean removeType(int typeId) throws Exception {
		
		boolean flag = typeDao.deleteType(typeId);
		CacheManager.getInstance().initTypes(queryTypes());
		return flag;
	}

	@Override
	public Type getType(int typeId) throws Exception {
		
		return typeDao.getType(typeId);
	}

	@Override
	public boolean updateType(Type type) throws Exception {
		
		type.setUpdateTime(new Date());
		boolean flag = typeDao.updateType(type);
		CacheManager.getInstance().initTypes(queryTypes());
		return flag;
	}

	@Override
	public boolean ascTdescType(int ascId, int descIt) throws Exception {
		try {
			
			Type ascG = typeDao.getType(ascId);
			Type descG = typeDao.getType(descIt);
			
			int ascO = ascG.getOrderNo();
			int descO = descG.getOrderNo();
			if(ascO > descO){
				return true;
			}
			ascG.setOrderNo(descO);
			descG.setOrderNo(ascO);
			
			typeDao.updateType(ascG);
			typeDao.updateType(descG);
			//更改缓存数据
			CacheManager.getInstance().initTypes(queryTypes());
			
			return true;
		} catch (Exception e) {
			log.error("分类升序降序失败",e);
		}
		return false;
	}

	@Override
	public List<Type> queryTypes() throws Exception {
		
		return typeDao.queryTypes();
	}

	@Override
	public List<Type> queryTypesByGroup(int groupId) throws Exception {
		return typeDao.queryTypesByGroup(groupId);
	}
	
	@Override
	public Map<String, Object> loadIndex(int group) throws Exception {
		Map<String, Object> data = Maps.newHashMap();
		List<Type> types = null;
		if(group == 0){//取所有分类
			types = typeDao.queryTypes();
			data.put("group", "-1");
		}else{//只取分组下的分类
			types = typeDao.queryTypesByGroup(group);
			data.put("group", group);
		}
		
		data.put("types", types);
		data.put("groups", groupDao.queryGroups());
		
		return data;
	}
	
	@Override
	public Map<String, Object> loadUpdate(int typeId) throws Exception {
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("type", typeDao.getType(typeId));
		data.put("groups", groupDao.queryGroups());
		return data;
	}
	
}
