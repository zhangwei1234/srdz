package com.zw.srdz.service;

import java.util.List;
import java.util.Map;

import com.zw.srdz.domain.Type;

/**
* 项目名称：srdz-service   
* 类名称：HomeService   
* 类描述：   前端界面显示 服务层接口
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-2-2 上午11:08:52   
* 修改人：zhangwei
* 修改时间：2015-2-2 上午11:08:52   
* 修改备注：   
* @version    
*
 */
public interface HomeService {

	/**
	 * 点击分组显示 分类界面所需的数据
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loadGroup(int group, List<Type> types) throws Exception;
	
	/**
	 * 加载残品数据
	 * @param type
	 * @param display
	 * @param order
	 * @param start
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loadList(int type,int display,int order,int start) throws Exception;
	
	/**
	 * 记录一次商品点击
	 * @param id
	 */
	public void clickProduct(int id);
	
	/**
	 * 记录一次商品点赞
	 * @param id
	 */
	public void pariseProduct(int id);
}
