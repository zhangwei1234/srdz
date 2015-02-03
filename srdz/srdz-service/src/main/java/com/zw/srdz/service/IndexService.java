package com.zw.srdz.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zw.srdz.dao.AdvertisingDao;
import com.zw.srdz.dao.GroupDao;
import com.zw.srdz.dao.TypeDao;
import com.zw.srdz.service.base.BaseService;
import com.zw.srdz.service.cache.CacheManager;
import com.zw.srdz.service.task.TaskManager;

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
	
	@Resource private TaskManager taskManager;
	@Resource private GroupDao groupDao;
	@Resource private TypeDao typeDao;
	@Resource private AdvertisingDao advertisingDao;
	
	/**
	 * 系统初始化
	 */
	public void init(){
		log.info("系统初始化----------------------->");
		try {
			CacheManager.getInstance();
			CacheManager.getInstance().initGroups(groupDao.queryGroups());
			CacheManager.getInstance().initTypes(typeDao.queryTypes());
			CacheManager.getInstance().initAdvertising(advertisingDao.list());
			taskManager.start();
			
			//启动线程每隔10秒钟从数据库跟新一次缓存数据
			Thread thd = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						log.info("----------------->begin refresh db data to cache");
						try {
							CacheManager.getInstance().initGroups(groupDao.queryGroups());
							CacheManager.getInstance().initTypes(typeDao.queryTypes());
							CacheManager.getInstance().initAdvertising(advertisingDao.list());
							Thread.sleep(1000*60);
						} catch (Exception e) {
							log.error("跟新缓存数据失败.", e);
						}
					}
				}
			});
			
			thd.start();
		} catch (Exception e) {
			log.error("系统初始化失败.", e);
			System.exit(0);
		}
		log.info("系统初始化完成-------------------->");
	}
}
