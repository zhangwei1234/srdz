package com.zw.srdz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;

/**
* 项目名称：srdz-service   
* 类名称：IndexService   
* 类描述：   初始化系统信息
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-30 上午10:38:03   
* 修改人：zhangwei
* 修改时间：2015-1-30 上午10:38:03   
* 修改备注：   
* @version    
*
 */
public class IndexService extends BaseService{

	private Logger log = LoggerFactory.getLogger(IndexService.class);
	
	/**
	 * 系统初始化
	 */
	public void init(){
		log.info("系统初始化----------------------->");
		CacheManager.getInstance();
		log.info("系统初始化完成-------------------->");
	}
}
