package com.zw.topic.dao.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zw.srdz.common.util.DateUtil;
import com.zw.srdz.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:topic-dao.xml"})
public class MongoTest {

	@Resource(name="mongoTemplete")
	private MongoOperations template;
	
	@Test
	public void testConnection(){
		System.out.println(template);
	}
	
	@Test
	public void saveUser(){
		for(int i=50;i<158;i++){
			User user = new User();
			user.setAccount("admin"+i);
			user.setName("系统管理员"+i);
			user.setPassword("123456");
			user.setSex(2);
			user.setEmail("zhangwei_2943@163.com"+i);
			user.setBirthday(new Date());
			user.setCreateTime(new Date());
			
			template.save(user);
		}
		
		System.out.println("svae ok");
	}
	
	@Test
	public void findUser(){
		List<User> users = template.findAll(User.class);
		
		for(User user:users){
			System.out.println("id:"+user.getId()+",account:"+user.getAccount()+",name:"+user.getName()+",pwd:"+user.getPassword()+",sex:"+user.getSex()+",email:"+user.getEmail()+",birthday:"+DateUtil.dateFormat(user.getBirthday())+",createTime:"+DateUtil.dateFormat(user.getCreateTime()));
		}
	}
	
	@Test
	public void searchUser(){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is("54375311a61fcae81b6ac7c01"));
		List<User> users = template.find(query, User.class);
		
		for(User user:users){
			System.out.println("id:"+user.getId()+",account:"+user.getAccount()+",name:"+user.getName()+",pwd:"+user.getPassword()+",sex:"+user.getSex()+",email:"+user.getEmail()+",birthday:"+DateUtil.dateFormat(user.getBirthday())+",createTime:"+DateUtil.dateFormat(user.getCreateTime()));
		}
	}
	
	@Test
	public void countUser(){
		System.out.println(template.count(null, User.class));
	}
}
