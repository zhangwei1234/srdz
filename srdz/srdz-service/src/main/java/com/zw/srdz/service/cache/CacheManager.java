package com.zw.srdz.service.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zw.srdz.domain.Group;
import com.zw.srdz.domain.Type;

public class CacheManager {

	private Logger log = LoggerFactory.getLogger(CacheManager.class);
	
	private static CacheManager instance;
	private List<Group> groups;
	private Map<Object, List<Type>> types;
	private ReadWriteLock groupsLock = new ReentrantReadWriteLock();
	private ReadWriteLock typesLock = new ReentrantReadWriteLock();
	
	private CacheManager(){
		this.groups = Lists.newArrayList();
		this.types = Maps.newHashMap();
		System.out.println("cache manager init");
		log.info("cache manager init");
	}
	
	private static synchronized void create(){
		if (null == instance){
			instance = new CacheManager();
		}
	}
	
	/**
	 * 获取cache管理对象
	 * @return
	 */
	public static CacheManager getInstance(){
		if (null == instance){
			create();
		}
		return instance;
	}
	
	/**
	 * 初始化group信息到缓存
	 * @param groups
	 * @throws Exception
	 */
	public void initGroups(List<Group> groups) throws Exception{
		try {
			groupsLock.writeLock().lock();
			if(null == this.groups){
				this.groups = Lists.newArrayList();
			}else if(!this.groups.isEmpty()){
				this.groups.clear();
			}
			//排序
			Collections.sort(groups,new Comparator<Group>() {
				@Override
				public int compare(Group o1, Group o2) {
					if( o1.getOrderNo()> o2.getOrderNo()){
						return 0;
					}
					return 1;
				}
			});
			
			this.groups.addAll(groups);
		} catch (Exception e) {
			throw e;
		}finally{
			groupsLock.writeLock().unlock();
		}
	}
	
	/**
	 * 获取所有分组信息,排序后的
	 * @return
	 * @throws Exception
	 */
	public List<Group> getGroups() throws Exception{
		try {
			groupsLock.readLock().lock();
			return this.groups;
		} catch (Exception e) {
			throw e;
		}finally{
			groupsLock.readLock().unlock();
		}
	}
	
	/**
	 * 初始化分类信息到缓存
	 * @param types
	 * @throws Exception
	 */
	public void initTypes(List<Type> types) throws Exception{
		try {
			typesLock.writeLock().lock();
			if(null == this.types){
				this.types = Maps.newHashMap();
			}else if (!this.types.isEmpty()){
				this.types.clear();
			}
			
			for(Type type : types){
				int groupId = type.getGroupId();
				List<Type> ts = this.types.get(groupId);
				if (null == ts){
					ts = Lists.newArrayList();
				}
				ts.add(type);
				this.types.put(groupId, ts);
			}
			
			//排序
			Iterator<Object> keys = this.types.keySet().iterator();
			while(keys.hasNext()){
				Object key = keys.next();
				List<Type> ts = this.types.get(key);
				Collections.sort(ts, new Comparator<Type>() {
					@Override
					public int compare(Type o1, Type o2) {
						if(o1.getOrderNo() > o2.getOrderNo()){
							return 0;
						}
						return 1;
					}
				});
			}
		} catch (Exception e) {
			throw e;
		}finally{
			typesLock.writeLock().unlock();
		}
	}
	
	/**
	 * 根据分组获取所有分类信息,排序后
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List<Type> getTypes(int groupId) throws Exception{
		try {
			typesLock.readLock().lock();
			
			List<Type> ts = this.types.get(groupId);
			if (null == ts){
				return Lists.newArrayList();
			}
			return ts;
		} catch (Exception e) {
			throw e;
		}finally{
			typesLock.readLock().unlock();
		}
	}
}
