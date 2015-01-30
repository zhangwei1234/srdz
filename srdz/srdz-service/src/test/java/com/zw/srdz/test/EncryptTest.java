package com.zw.srdz.test;

import com.alibaba.fastjson.JSONObject;
import com.zw.srdz.common.util.Encrypt;
import com.zw.srdz.domain.User;

import junit.framework.TestCase;

public class EncryptTest extends TestCase{

	public void testEncodes() throws Exception{
		User user = new User();
		user.setAccount("admin");
		user.setName("超级管理员");
		user.setEmail("admin@.jd.com");
		
		String s = JSONObject.toJSONString(user);
		System.out.println("加密前数据: "+s);
		String encode = Encrypt.encodeDes(s);
		System.out.println("加密后数据: "+encode);
		
	}
	
	public void testDecode() throws Exception{
		String s = "54B257A9187952CB09F33877FFDC744590729A994D7F12649A61DAA1EA1DE5FDD80949277635ADBD7D9ACFFC1EE0780F2B1B2736DD5A5DD51B6917937540768D19FBD5CB0B0525C0BBD7BF26B06DBDB1D14660A37C6A0F438C0E01467FC900506C83BBE8015F7E9F6A040D69B04C2CBB";
		
		//解密数据
		String decode = Encrypt.decodeDes(s);
		System.out.println("解码后数据:"+decode);
	}
}
