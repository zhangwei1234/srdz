package com.zw.srdz.service.task;

public interface Task {

	/**
	 * 前置函数
	 * @throws Exception
	 */
	public void before() throws Exception;
	
	/**
	 * 后置函数
	 * @throws Exception
	 */
	public void after() throws Exception;
	
	/**
	 * 执行函数
	 * @return
	 * @throws Exception
	 */
	public boolean execute() throws Exception;
	
	/**
	 * 设置执行异常的异常对象
	 */
	public void setException(Exception e) ;
	
	/**
	 * 设置任务的执行状态
	 * @param state
	 */
	public void setExecuteStatus(boolean state);
	
	/**
	 * 获取任务基本信息
	 * @return
	 */
	public String getInfo();
	
	/**
	 * 记录执行开始时间
	 * @param time
	 */
	public void setStartTime(long time);
	
	/**
	 * 记录任务执行完成时间
	 * @param time
	 */
	public void setEndTime(long time);
	
	/**
	 * 获取任务执行信息
	 * @return
	 */
	public String getExecuteInfo();
}
