package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.zw.srdz.dao.UserFeedBackDao;
import com.zw.srdz.dao.base.BaseDao;
import com.zw.srdz.domain.UserFeedBack;

@Repository("userFeedBack")
public class UserFeedBackDaoImpl  extends BaseDao implements UserFeedBackDao{

	private final String base = "com.zw.srdz.dao.UserFeedBackDao";
	
	@Override
	public boolean addFeedBack(UserFeedBack feedBack) throws Exception {
		
		feedBack.setStatus(-1);
		feedBack.setCrateTime(new Date());
		
		return template.insert(getNameSpace(this.base, "ADD"), feedBack) >0;
	}

	@Override
	public boolean executeFeedBack(String account, String operateComment, int id)throws Exception {
		Map<String, Object> param = Maps.newHashMap();
		param.put("operateUser", account);
		param.put("updateTime", new Date());
		param.put("operateComment", operateComment);
		param.put("id", id);
		
		return template.update(getNameSpace(this.base, "EXECUTE_FEED_BACK"), param) >0;
	}

	@Override
	public List<UserFeedBack> queryFeedBackByAccount(String account)throws Exception {
		return template.selectList(getNameSpace(this.base, "QUERY_FEED_BACK_ACCOUNT"), account);
	}

	@Override
	public List<UserFeedBack> queryFeedBack(int status, int start, int length)throws Exception {
		Map<String, Object> param = Maps.newHashMap();
		param.put("start", start);
		param.put("length", length);
		param.put("status", status);
		if(status != 0){
			return template.selectList(getNameSpace(this.base, "QUERY_FEED_BACK_PAGE"), param);
		}
		return template.selectList(getNameSpace(this.base, "QUERY_FEED_BACK_ALL_PAGE"), param);
	}

	@Override
	public int queryFeedBack(int status) throws Exception {
		Object o = null;
		if(status != 0){
			o = template.selectOne(getNameSpace(this.base, "QUERY_FEED_BACK_COUNT_PAGE"), status);
		}else{
			o = template.selectOne(getNameSpace(this.base, "QUERY_FEED_BACK_ALL_COUNT_PAGE"));
		}
		if(o != null){
			return (Integer)o;
		}
		return 0;
	}

}
