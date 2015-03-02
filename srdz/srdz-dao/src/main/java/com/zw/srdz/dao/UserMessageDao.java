package com.zw.srdz.dao;

import java.util.List;

import com.zw.srdz.domain.UserMessage;

/**
 * 用户信息接口
 * @author wei
 *
 */
public interface UserMessageDao {

	/**
	 * 添加一条用户消息
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public boolean addMessage(UserMessage msg) throws Exception;
	
	/**
	 * 查询所有的用户消息,如果account is null 则查询所有的
	 * @param account
	 * @param start
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public List<UserMessage> queryMessage(String account, int start, int length) throws Exception;
	
	/**
	 * 查询所有的用户消息数量,如果account is null 则查询所有的
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public int queryMessage(String account) throws Exception;
	
	/**
	 * 查询一个用户的所有未读取的消息数据
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public List<UserMessage> queryMessageByAccount(String account) throws Exception;
	
	/**
	 * 查询一个用户的所有未读取的消息数据
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public int queryMessageCountByAccount(String account) throws Exception;
	
	/**
	 * 读取一条消息数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean readMessage(int id) throws Exception;
}
