package com.zw.srdz.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 * 
 * @author cdzhangwei3
 * 
 */
public class DateUtil {

	/**
	 * 时间格式转换(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {

		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 时间格式转换(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatyMd(Date date) {

		return dateFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期对象格式化为指定的字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormat(Date date, String format) {
		if (date == null || format == null) {
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
	 * 将字符串按照指定的格式转换成Date对象
	 * 
	 * @param data
	 * @param format
	 * @return
	 */
	public static Date dataFormat(String data, String format) {
		if (data == null || format == null) {
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
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatStr2Date(String date) {
		return dataFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 将字符串按照格式(yyyy-MM-dd HH:mm:ss)转换成时间对象
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static Date dataFormat(String data) {
		return dataFormat(data, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将字符串按照格式(yyyy-MM-dd) 转换成世界对象
	 * 
	 * @param data
	 * @return
	 */
	public static Date dateFormatyMd(String data) {
		return dataFormat(data, "yyyy-MM-dd");
	}
}
