package com.zw.srdz.domain.author;

import com.alibaba.fastjson.JSONObject;
import com.zw.srdz.common.util.Encrypt;

/**
* 项目名称：srdz-domain   
* 类名称：LoginContextEncrypt   
* 类描述：   负责登陆上下文的 加密和解密工作
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-30 上午10:01:19   
* 修改人：zhangwei
* 修改时间：2015-1-30 上午10:01:19   
* 修改备注：   
* @version    
*
 */
public final class LoginContextEncrypt {

	/**
	 * 将登陆上下文加密成字符串
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String encodingContext(LoginContext context) throws Exception{
	
		String json = JSONObject.toJSONString(context);
		
		return Encrypt.encodeDes(json);
	}
	
	/**
	 * 将加密字符串解密成上下文对象
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static LoginContext decodeContext(String str) throws Exception{
		str = Encrypt.decodeDes(str);
		return JSONObject.parseObject(str, LoginContext.class);
	}
}
