package com.zw.srdz.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密算法工具类
 * 
 * @author cdzhangwei3
 * 
 */
public class Encrypt {

	private static final Logger LOG = LoggerFactory.getLogger(Encrypt.class);

	public static String encodeURI(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}
	}

	public static String decodeURI(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * MD5加密算法(16位,ERP密码为16位)
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeMD5(String str) {
		String temp = encodemd5(str);
		if (temp != null) {
			return temp.substring(8, 24);// 16位的加密
		}
		return temp;
	}

	/**
	 * MD5加密算法(32位)
	 * 
	 * @param str
	 * @return
	 */
	public static String encodemd5(String str) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			StringBuffer buf = new StringBuffer("");
			for (int i : b) {
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			dstr = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return dstr;// 32位加密
	}

	/**
	 * 加密DES字符串
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public static String encodeDes(String strIn) {
		try {
			Cipher cipher = getDesCipher("encrypt");
			return byteArr2HexStr(cipher.doFinal(strIn.getBytes()));
		} catch (Exception e) {
			LOG.error("加密DES[" + strIn + "]失败", e);
			return null;
		}
	}

	/**
	 * 加密DES字符串
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public static String decodeDes(String strIn) {
		try {
			Cipher cipher = getDesCipher("decode");
			return new String(cipher.doFinal(hexStr2ByteArr(strIn)));
		} catch (Exception e) {
			LOG.error("解密DES[" + strIn + "]失败", e);
			return null;
		}
	}

	private static Cipher getDesCipher(String type) {
		Cipher cipher = null;
		try {
			// 实例化DES密钥
			DESKeySpec dks = new DESKeySpec("*&uim^_^360_&".getBytes());
			// 实例化密钥工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 生成密钥
			SecretKey secretKey = keyFactory.generateSecret(dks);
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");// 加密/解密/工作模式/填充方式
			if ("encrypt".equals(type)) {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
		} catch (Exception e) {
			LOG.error("初始化DES-Cipher失败", e);
		}
		return cipher;
	}

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16).toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	private static final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7' };

	private static final byte[] DECODE_TABLE;

	static {
		DECODE_TABLE = new byte[128];

		for (int i = 0; i < DECODE_TABLE.length; i++) {
			DECODE_TABLE[i] = (byte) 0xFF;
		}
		for (int i = 0; i < ALPHABET.length; i++) {
			DECODE_TABLE[(int) ALPHABET[i]] = (byte) i;
			if (i < 24) {
				DECODE_TABLE[(int) Character.toLowerCase(ALPHABET[i])] = (byte) i;
			}
		}
	}

	public static byte[] Base32Decode(String s) throws Exception {

		char[] stringData = s.toCharArray();
		byte[] data = new byte[(stringData.length * 5) / 8];

		for (int i = 0, j = 0, index = 0; i < stringData.length; i++) {
			int val;

			try {
				val = DECODE_TABLE[stringData[i]];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new Exception("Illegal character");
			}

			if (val == 0xFF) {
				throw new Exception("Illegal character");
			}

			if (index <= 3) {
				index = (index + 5) % 8;
				if (index == 0) {
					data[j++] |= val;
				} else {
					data[j] |= val << (8 - index);
				}
			} else {
				index = (index + 5) % 8;
				data[j++] |= (val >> index);
				if (j < data.length) {
					data[j] |= val << (8 - index);
				}
			}
		}
		return data;
	}
	
	public static String Base64Decode(String plaintext, String key) throws Exception {
		//32解码
		byte[] outputBuf = Base32Decode(plaintext);

		// BASE64解密key
		byte[] decodeBase64 = Base64.decodeBase64(key.getBytes("UTF-8"));

		// BASE64解密
		SecretKey secretKey = new SecretKeySpec(decodeBase64, "DESede");
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptPlainText = cipher.doFinal(outputBuf);
		String decry = new String(decryptPlainText, "utf-8");
		return decry;
	}

	public static void main(String[] args) {
		System.out.println(encodeMD5("abcd1234aaaaaaaaaaaaaaa"));
	}
}
