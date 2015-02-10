package com.zw.srdz.service.task;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 任务管理器,多线程执行
 * @author wei
 *
 */
public class TaskManager {

	private Logger log = LoggerFactory.getLogger(TaskManager.class);
	@Value(value="${max.queue.size}")
	private int maxQueueSize;
	@Value(value="${max.work.thread}")
	private int maxWorkThread;
	
	private ThreadPoolExecutor pool;
	
	/**
	 * 启动任务管理器
	 * @throws Exception
	 */
	public void start()throws Exception{
		log.info("开始初始化任务管理对象---------------------->");
		LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<Runnable>(this.maxQueueSize);
		this.pool = new ThreadPoolExecutor(1, this.maxWorkThread, 5, TimeUnit.SECONDS, queue);
		log.info("任务管理对象初始化完成---------------------->");
	}
	
	/**
	 * 停止任务管理对象
	 * @throws Exception
	 */
	public void stop() throws Exception{
		if(null == this.pool){
			log.warn("停止任务管理对象失败,fail:任务管理对象还未初始化");
			return;
		}
		this.pool.shutdown();
	}
	
	/**
	 * 添加一个待执行任务
	 * @param task
	 * @return
	 */
	public boolean addTask(Task task) {
		try {
			if (null == task){
				return false;
			}
			ExecuteTask cmd = new ExecuteTask(task);
			this.pool.execute(cmd);
			log.debug("添加任务:"+task.getInfo()+" success");
			
			return true;
		} catch (Exception e) {
			log.error("添加任务:"+task.getInfo()+"失败.",e);
			return false;
		}
	}
}

/**
 * 任务执行对象
 * @author wei
 *
 */
class ExecuteTask implements Runnable{
	
	private Task task;
	private Logger log = LoggerFactory.getLogger(ExecuteTask.class);
	
	public ExecuteTask(Task task) {
		this.task = task;
	}
	
	@Override
	public void run() {
		this.task.setStartTime(System.currentTimeMillis());
		//执行前置任务
		try {
			this.task.before();
		} catch (Exception e) {
			log.error("执行:"+this.task.getInfo()+" 前置任务失败.", e);
		}
		
		//执行任务
		try {
			boolean state = this.task.execute();
			this.task.setExecuteStatus(state);
			
		} catch (Exception e) {
			log.error("执行:"+this.task.getInfo()+" 任务失败.", e);
			this.task.setException(e);
		}
		
		//执行后置任务
		try {
			this.task.after();
		} catch (Exception e) {
			log.error("执行:"+this.task.getInfo()+" 后置任务失败.", e);
		}
		
		this.task.setEndTime(System.currentTimeMillis());
		
		log.info(this.task.getExecuteInfo());
	}
}
