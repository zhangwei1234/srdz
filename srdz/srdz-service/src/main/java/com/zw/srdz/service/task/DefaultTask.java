package com.zw.srdz.service.task;

@SuppressWarnings("all")
public abstract class DefaultTask implements Task{

	private String name;
	private Exception ex;
	private boolean state;
	private long createTime;//任务创建时间
	private long startTime;//执行开始时间
	private long endTime;//执行结束时间
	
	public DefaultTask(String name){
		this.createTime = System.currentTimeMillis();
		this.name = name;
	}
	
	@Override
	public String getInfo() {
		return new StringBuffer("[").append(this.name).append("]").toString();
	}
	
	public void setException(Exception e) {
		this.ex = e;
	}
	
	@Override
	public void setExecuteStatus(boolean state) {
		this.state = state;
	}
	
	@Override
	public void setStartTime(long time) {
		this.startTime = time;
	}
	
	@Override
	public void setEndTime(long time) {
		this.endTime = time;
	}
	
	@Override
	public String getExecuteInfo() {
		return new StringBuffer("任务:").append(getInfo()).append("创建时间:[").append(this.createTime).append("], 执行开始时间:[").append(this.startTime).append("], 执行结束时间:[").append(this.endTime).append("],等待执行时间:[(").append(this.startTime-this.createTime).append(")毫秒],执行耗时:[(").append(this.endTime-this.startTime).append(")毫秒], 执行:[").append(this.state?"成功":"失败").append("]").toString();
	}
}
