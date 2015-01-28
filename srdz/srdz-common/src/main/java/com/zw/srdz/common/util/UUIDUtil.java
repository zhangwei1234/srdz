package com.zw.srdz.common.util;

import java.util.UUID;

/**
 * 获取32位唯一编码
 * 
 * @author cdzhangwei3
 *
 */
public class UUIDUtil {

	/**
	 * 获取32位唯一UID编码
	 * @return
	 */
	public static String getUid(){
		String uid = UUID.randomUUID().toString().replace("-", "");
		return uid;
	}
}
