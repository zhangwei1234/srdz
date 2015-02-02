package com.zw.srdz.common.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class TopicUtil {

	private static Logger log = LoggerFactory.getLogger(TopicUtil.class);
	
	/**
	 * 将标准格式的字符串(aa,bb,cc) 转换成list
	 * @param str
	 * @return
	 */
	public final static List<String> cvnStr2List(String str){
		
		if(StringUtils.isEmpty(str)){
			return null;
		}
		
		String[] array = str.split(",");
		if(null != array && array.length >0){
			List<String> list = Lists.newArrayList();
			for(String arr:array){
				list.add(arr);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 将标准格式的字符串(aa,bb,cc) 转换成list,int 类型
	 * @param str
	 * @return
	 */
	public final static List<Integer> cvnStr2ListInt(String str){
		List<Integer> list = Lists.newArrayList();
		if(StringUtils.isEmpty(str)){
			return list;
		}
		
		String [] arr = str.split(",");
		for(String s: arr){
			try {
				list.add(Integer.parseInt(s));
			} catch (Exception e) {
				log.warn("数据格式转换失败,["+s+"] 不是 int 类型");
			}
		}
		return list;
	}
}
