package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.zw.srdz.dao.UserMessageDao;
import com.zw.srdz.dao.base.BaseDao;
import com.zw.srdz.domain.UserMessage;

@Repository("userMessage")
public class UserMessageDaoImpl extends BaseDao implements UserMessageDao{

	private final String base = "com.zw.srdz.dao.UserMessageDao";
	
	@Override
	public boolean addMessage(UserMessage msg) throws Exception {
		msg.setStatus(-1);
		msg.setCreateTime(new Date());
		
		return template.insert(getNameSpace(this.base, "ADD"), msg) >0;
	}

	@Override
	public List<UserMessage> queryMessage(String account, int start, int length)throws Exception {
		Map<String, Object> param = Maps.newHashMap();
		param.put("start", start);
		param.put("length", length);
		param.put("account", account);
		
		if(StringUtils.isEmpty(account)){
			return template.selectList(getNameSpace(this.base, "QUERY_MSG_ALL_PAGE"), param);
		}else{
			return template.selectList(getNameSpace(this.base, "QUERY_MSG_ACCOUNT_PAGE"), param);
		}
		
	}

	@Override
	public int queryMessage(String account) throws Exception {
		Object o = null;
		if(StringUtils.isEmpty(account)){
			o = template.selectOne(getNameSpace(this.base, "QUERY_MSG_ALL_COUNT"));
		}else{
			o = template.selectOne(getNameSpace(this.base, "QUERY_MSG_ACCOUNT_PAGE"), account);
		}
		
		if (null != o){
			return (Integer)o;
		}
		
		return 0;
	}

	@Override
	public List<UserMessage> queryMessageByAccount(String account)throws Exception {
		return template.selectList(getNameSpace(this.base, "QUERY_MSG_ACCOUNT_ALL"), account);
	}

	@Override
	public int queryMessageCountByAccount(String account) throws Exception {
		Object o = template.selectOne(getNameSpace(this.base, "QUERY_MSG_ACCOUNT_COUNT_ALL"), account);
		if (null != o){
			return (Integer)o;
		}
		return 0;
	}

	@Override
	public boolean readMessage(int id) throws Exception {
		
		Map<String, Object> param = Maps.newHashMap();
		param.put("updateTime", new Date());
		param.put("id", id);
		
		return template.update(getNameSpace(this.base, "READ_MSG"), param) >0;
	}

	
}
