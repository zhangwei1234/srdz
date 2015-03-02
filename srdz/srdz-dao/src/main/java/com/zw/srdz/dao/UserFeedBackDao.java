package com.zw.srdz.dao;

import java.util.List;
import com.zw.srdz.domain.UserFeedBack;

public interface UserFeedBackDao {

	/**
	 * 插入反馈信息
	 * @param feedBack
	 * @return
	 * @throws Exception
	 */
	public boolean addFeedBack(UserFeedBack feedBack) throws Exception;
	
	/**
	 * 处理一条消息
	 * @param account
	 * @param operateComment 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean executeFeedBack(String account, String operateComment, int id) throws Exception;
	
	/**
	 * 查询一个用户反馈的所有记录,按照反馈时间排序
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public List<UserFeedBack> queryFeedBackByAccount(String account) throws Exception;
	
	/**
	 * 分页查询所有用户反馈的信息,如果type=0 则查询所有的数据
	 * @param status
	 * @param start
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public List<UserFeedBack> queryFeedBack(int status, int start, int length) throws Exception;
	
	/**
	 * 查询反馈数量,如果type=0 则查询所有的数据
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int queryFeedBack(int status) throws Exception;
}
