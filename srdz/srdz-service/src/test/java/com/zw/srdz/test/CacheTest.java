package com.zw.srdz.test;

import java.util.List;
import com.google.common.collect.Lists;
import com.zw.srdz.domain.Group;
import com.zw.srdz.domain.Type;
import com.zw.srdz.service.cache.CacheManager;
import junit.framework.TestCase;

public class CacheTest extends TestCase{

	public void testGroup() throws Exception{
		CacheManager manager = CacheManager.getInstance();
		Group g = new Group();
		g.setName("name-1");
		g.setOrderNo(1);
		
		Group g1 = new Group();
		g1.setName("name-19");
		g1.setOrderNo(5);
		
		Group g2 = new Group();
		g2.setName("name-3");
		g2.setOrderNo(46);
		
		List<Group> groups = Lists.newArrayList();
		groups.add(g2);
		groups.add(g);
		groups.add(g1);
		
		manager.initGroups(groups);
		
		for(Group gp : manager.getGroups()){
			System.out.println("------>:"+gp.getName()+", orderId:"+gp.getOrderNo());
		}
	}
	
	public void testType() throws Exception{
		CacheManager manager = CacheManager.getInstance();
		
		int [] groups= {2,1,5,3,4};
		List<Type> types = Lists.newArrayList();
		for(int g:groups){
			for(int a: groups){
				Type t = new Type();
				t.setGroupId(g);
				t.setName("type-"+a);
				t.setOrderNo(a);
				types.add(t);
			}
		}
		
		manager.initTypes(types);
		
		for(int g : groups){
			List<Type> ts = manager.getTypes(g);
			System.out.println("=================group:"+g+"======================");
			for(Type type: ts){
				System.out.println("--------->group:"+type.getGroupId()+",name:"+type.getName()+", orderId:"+type.getOrderNo());
			}
			System.out.println("=================================================");
		}
	}
	
	public void testTypes() throws Exception{
		for(int i=0;i<5;i++){
			testType();
			System.out.println("-------------------------------------------------------------------------------------------");
		}
	}
}
