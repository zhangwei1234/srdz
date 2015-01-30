package com.zw.srdz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.zw.srdz.dao.GroupDao;
import com.zw.srdz.dao.TypeDao;
import com.zw.srdz.domain.Group;
import com.zw.srdz.service.GroupService;
import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;

@Service
public class GroupServiceImpl extends BaseService implements GroupService{

	private Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Resource private GroupDao groupDao;
	@Resource private TypeDao typeDao;
	
	@Override
	public Group getGroup(String groupId) throws Exception {
		int id = Integer.parseInt(groupId);
		return groupDao.getGroup(id);
	}
	
	@Override
	public List<Group> listGroups() throws Exception {
		
		return groupDao.queryGroups();
	}

	@Override
	public boolean addGroup(Group group) throws Exception {
		
		boolean flag = groupDao.addGroup(group);
		//更改缓存数据
		CacheManager.getInstance().initGroups(groupDao.queryGroups());
		return flag;
	}

	@Override
	public boolean updateGroup(Group group) throws Exception {
		
		boolean flag =  groupDao.updateGroup(group);
		//更改缓存数据
		CacheManager.getInstance().initGroups(groupDao.queryGroups());
		return flag;
	}

	@Override
	public boolean removeGroup(String groupId) throws Exception {
		int id= 0;
		try {
			id = Integer.parseInt(groupId);
		} catch (Exception e) {
			log.error("删除group失败, groupId:"+groupId+" is not Integer");
			return false;
		}
		
		//判断分组下是否存在分类
		if(typeDao.existsTypeByGroup(id)){
			log.warn("删除分组失败,分组中还存在分类数据");
			return false;
		}
		boolean flag =  groupDao.deleteGroup(id);
		//更改缓存数据
		CacheManager.getInstance().initGroups(groupDao.queryGroups());
		
		return flag;
	}
	
	/**
	 * 升序&降序，每次只能升降一个名次
	 * @param ascId 升
	 * @param descId 降
	 * @return
	 * @throws Exception
	 */
	public boolean ascTDesc(String ascId,String descId) throws Exception{
		
		try {
			
			int asc = Integer.parseInt(ascId);
			int desc = Integer.parseInt(descId);
			
			Group ascG = groupDao.getGroup(asc);
			Group descG = groupDao.getGroup(desc);
			
			int ascO = ascG.getOrderNo();
			int descO = descG.getOrderNo();
			if(ascO > descO){
				return true;
			}
			ascG.setOrderNo(descO);
			descG.setOrderNo(ascO);
			
			groupDao.updateGroup(ascG);
			groupDao.updateGroup(descG);
			//更改缓存数据
			CacheManager.getInstance().initGroups(groupDao.queryGroups());
			return true;
		} catch (Exception e) {
			log.error("分组升序降序失败",e);
		}
		return false;
	}
}
