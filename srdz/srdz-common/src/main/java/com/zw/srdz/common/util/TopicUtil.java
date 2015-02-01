package com.zw.srdz.common.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class TopicUtil {

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
	
}
