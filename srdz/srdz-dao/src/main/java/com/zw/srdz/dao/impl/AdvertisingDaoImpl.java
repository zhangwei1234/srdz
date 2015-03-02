package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zw.srdz.dao.AdvertisingDao;
import com.zw.srdz.dao.base.BaseDao;
import com.zw.srdz.domain.Advertising;

@Repository("advertisingDao")
public class AdvertisingDaoImpl extends BaseDao implements AdvertisingDao{

	private final String base = "com.zw.srdz.dao.AdvertisingDao." ;
	
	
	@Override
	public boolean add(Advertising advertising) throws Exception {
		advertising.setCreateTime(new Date());
		return template.insert(getNameSpace(this.base, "ADD_ADVERTISING"), advertising) > 0;
	}


	@Override
	public Advertising get(int id) throws Exception {
		
		return template.selectOne(getNameSpace(this.base, "QUERY_BY_ID"), id);
	}


	@Override
	public boolean update(Advertising advertising) throws Exception {
		
		return template.update(getNameSpace(this.base, "UPDATE_ADVERTISING"), advertising) > 0;
	}


	@Override
	public List<Advertising> list() throws Exception {
		
		return template.selectList(getNameSpace(this.base, "QUERY_ADVERTISING"));
	}


	@Override
	public boolean batchDel(List<Integer> ids) throws Exception {
		
		return template.delete(getNameSpace(this.base, "DELETE_ADVERTISING"), ids) >0;
	}


	@Override
	public boolean batchActive(List<Integer> ids) throws Exception {
		
		return template.update(getNameSpace(this.base, "UPDATE_ADVERTISING_ACTIVE"), ids) > 0;
	}


	@Override
	public boolean batchDisabled(List<Integer> ids) throws Exception {
		
		return template.update(getNameSpace(this.base, "UPDATE_ADVERTISING_DISABLED"), ids) > 0;
	}

}
