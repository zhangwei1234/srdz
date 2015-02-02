package com.zw.srdz.service;

import java.util.List;

import com.zw.srdz.domain.Advertising;

public interface AdvertisingService {

	/**
	 * 新增一个广告信息
	 * @param advertising
	 * @return
	 * @throws Exception
	 */
	public boolean add(Advertising advertising) throws Exception;
	
	/**
	 * 根据ID获取一个广告数据信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Advertising get(int id) throws Exception;
	
	/**
	 * 修改一个广告信息
	 * @param advertising
	 * @return
	 * @throws Exception
	 */
	public boolean update(Advertising advertising) throws Exception;
	
	/**
	 * 查询所有被激活的广告信息
	 * @return
	 * @throws Exception
	 */
	public List<Advertising> list() throws Exception;
	
	/**
	 * 批量删除广告数据
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean batchDel(String ids) throws Exception;
	
	/**
	 * 批量激活广告数据
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean batchActive(String ids) throws Exception;
	
	/**
	 * 批量禁用广告数据
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean batchDisabled(String ids) throws Exception;
}
