package com.zw.srdz.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zw.srdz.dao.UserDao;
import com.zw.srdz.dao.base.BaseDao;
import com.zw.srdz.domain.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao{

	private final String base = "com.zw.srdz.dao.UserDao.";
	
	@Override
	public boolean addUser(User user) throws Exception {
		user.setCreateTime(new Date());
		return template.insert(getNameSpace(this.base, "ADD_USER"), user) >0;
	}
	
	@Override
	public User getUser(String account) throws Exception {
		return template.selectOne(getNameSpace(this.base, "QUERY_BY_ACCOUNT"), account);
	}
	
	@Override
	public List<User> queryUsers() throws Exception {
		return template.selectList(getNameSpace(this.base, "QUERY_USERS"));
	}
	
	@Override
	public User getUserLike(String account) throws Exception {
		return template.selectOne(getNameSpace(this.base, "QUERY_BY_ACCOUNT_LIKE"), account);
	}
	
}
