package com.zw.srdz.common;

/**
 * 常量池
 * @author cdzhangwei3
 *
 */
public class Constants {
	//域名替换标准
	public static final String DOMAIN_PROFIX = "_domain_";
	//客户端请求类型(json后缀)
	public static final String AJAX_JSON_SUFFIX = "ajax";
	//velocity模板文件后缀
	public static final String AJAX_VM_SUFFIX = ".vm";
	//项目根路径url在VM中的变量名
	public static final String BASE_URL_NAME = "base_url";
	//项目静态资源根路径URL在VM中的变量名称
	public static final String BASE_STATIC_URL_NAME = "base_static_url";
	//JSON响应的状态字段
	public static final String RESPONSE_JSON_STATUS_KEY = "RES_STATUS";
	//JSON响应的提示信息
	public static final String RESPONSE_JSON_MSG_KEY = "RES_MSG";
	//JSON响应异常标识
	public static final String RESPONSE_JSON_STATUS_FAIL = "fail";
	//JSON响应正常标识
	public static final String RESPONSE_JSON_STATUS_OK  ="ok";
	//velocity字符集编码
	public static final String VM_ENCODING_DEFAULT = "UTF-8";
	//velocity 模板content 标识
	public static final String VM_LAYOUT_CONTENT_KEY = "screen_content";
	//控制台模板VM配置
	public static final String VM_LAYOUT_CONSOLE = "layout.console";
	//首页模块
	public static final String VM_LAYOUT_INDEX = "layout.home";
	//console vms
	//控制台首页 VM配置
	public static final String VM_CONSOLE_HOME = "console/home";
	
	//静态资源文件使用的全局时间戳
	public static final String static_v =Long.toString(System.currentTimeMillis());
	
}
