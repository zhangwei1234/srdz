package com.zw.srdz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zw.srdz.common.util.TopicUtil;
import com.zw.srdz.dao.AdvertisingDao;
import com.zw.srdz.domain.Advertising;
import com.zw.srdz.service.AdvertisingService;
import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;

@Service
public class AdvertisingServiceImpl extends BaseService implements AdvertisingService{

	@Resource private AdvertisingDao advertisingDao;
	
	@Override
	public boolean add(Advertising advertising) throws Exception {
		advertising.setStatus(Advertising.STATUS_ACTIVE);
		advertisingDao.add(advertising);
		//更新缓存
		CacheManager.getInstance().initAdvertising(list());
		
		return true;
	}

	@Override
	public Advertising get(int id) throws Exception {
		return advertisingDao.get(id);
	}

	@Override
	public boolean update(Advertising advertising) throws Exception {
		
		advertisingDao.update(advertising);
		//跟新缓存数据
		CacheManager.getInstance().initAdvertising(list());
		
		return true;
	}

	@Override
	public List<Advertising> list() throws Exception {
		
		return advertisingDao.list();
	}

	@Override
	public boolean batchDel(String ids) throws Exception {
		
		List<Integer> is = TopicUtil.cvnStr2ListInt(ids);
		if(null == is || is.isEmpty()){
			return true;
		}
		
		advertisingDao.batchDel(is);
		//跟新缓存数据
		CacheManager.getInstance().initAdvertising(list());
		
		return true;
	}

	@Override
	public boolean batchActive(String ids) throws Exception {
		
		List<Integer> is = TopicUtil.cvnStr2ListInt(ids);
		if(null == is || is.isEmpty()){
			return true;
		}
		
		advertisingDao.batchActive(is);
		//跟新缓存数据
		CacheManager.getInstance().initAdvertising(list());
		
		return true;
		
	}

	@Override
	public boolean batchDisabled(String ids) throws Exception {
		
		List<Integer> is = TopicUtil.cvnStr2ListInt(ids);
		if(null == is || is.isEmpty()){
			return true;
		}
		
		advertisingDao.batchDisabled(is);
		//跟新缓存数据
		CacheManager.getInstance().initAdvertising(list());
		
		return true;
	}

	
}
