package com.zw.srdz.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;



/**
 * VM 工具对象
 * @author cdzhangwei3
 *
 */
@SuppressWarnings("all")
public class VelocityTools {

	static Logger LOG = LoggerFactory.getLogger(VelocityTools.class);
	
	/**
	 * 判断key在Map中是否存在
	 * @param data
	 * @param key
	 * @return
	 */
	@SuppressWarnings("all")
	public static String isExists(Map data,String key){
		if(data == null){
			return "false";
		}
		if(data.containsKey(key)){
			return "true";
		}
		return "false";
	}
	/**
	 * 将对象转换成JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if(obj == null){
			return "";
		}
		
		return JSONObject.toJSONString(obj);
	}
	/**
	 * 将字符串按照指定的格式转换成Date对象
	 * @param data
	 * @param format
	 * @return
	 */
	public static Date dateFormat(String data,String format){
		if(data == null || format == null){
			return null;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(data);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将字符串转换成日期类型,格式(yyyy-MM-dd)
	 * @param date
	 * @return
	 */
	public static Date formatStr2Date(String date){
		return dateFormat(date, "yyyy-MM-dd");
	}
	
	/**
	 * 将字符串按照格式(yyyy-MM-dd HH:mm:ss)转换成时间对象
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static Date dataFormat(String data){
		return dateFormat(data, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将字符串按照格式(yyyy-MM-dd) 转换成世界对象
	 * @param data
	 * @return
	 */
	public static Date dateFormatyMd(String data){
		return dateFormat(data, "yyyy-MM-dd");
	}
	
	/**
	 * 将日期对象格式化为指定的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormat(Date date,String format){
		if(date == null || format == null){
			return "";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
		
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss 格式返回当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		return dateFormat(new Date());
	}
	
	/**
	 * 时间格式转换(yyyy-MM-dd HH:mm:ss)
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date){
		
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 时间格式转换(yyyy-MM-dd)
	 * @param date
	 * @return
	 */
	public static String dateFormatyMd(Date date){
		
		return dateFormat(date, "yyyy-MM-dd");
	}
	
	/**
	 * 判断两个字符串是否相等
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static String equals(String arg1,String arg2){
		if(arg1==null){arg1="a";}
		if(arg2==null){arg2="a";}
		
		return arg1.equals(arg2)?"true":"false";
	}
	
	/**
	 * 判断arg1 是否包含在arg2中
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static String indexOf(String arg1,String arg2){
		if(arg1==null){arg1="a";}
		if(arg2==null){arg2="a";}
		
		return arg2.indexOf(arg1)>=0?"true":"false";
	}
	
	/**
	 * 判断是否为空
	 * @param arg
	 * @return
	 */
	public static String isEmpty(String arg){
		if(arg == null) return "true";
		if("".equals(arg)) return "true";
		return "false";
	}
	
	/**
	 * 将字符串转化为指定格式的日期  
	 * @param date
	 * @return 
	 */
	public static Date stringTodate(String stringDate, String dataFormat) throws Exception{
		
		return dateFormat(stringDate, dataFormat);
	}
	
	/**
	 * 将字符串转换成int类型,异常返回0
	 * @param value
	 * @return
	 */
	public static int parseInt(String value){
		if(value == null || "".equals(value)){
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static String listEmpty(List<Object> list) {
		try {
			if(null == list || list.size() ==0){
				return "true";
			}
			return "false";
		} catch (Exception e) {
			return "true";
		}
	}
	
	/**
	 * 判断在list中是否存在
	 * @param targ
	 * @param s
	 * @return
	 */
	public static String isList(List<String> targ, String s){
		try {
			if(null == targ || targ.size() ==0){
				return "false";
			}
			for(String a:targ){
				if(a.equals(s)){
					return "true";
				}
			}
			return "false";
		} catch (Exception e) {
			return "false";
		}
	}
}
