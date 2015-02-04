package com.zw.srdz.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zw.srdz.common.Constants;
import com.zw.srdz.common.util.VelocityTools;
import com.zw.srdz.domain.Page;
import com.zw.srdz.domain.author.AuthorContext;


/**
 * Controller  公共组件,所有Controller必须继承至该对象
 * 
 * @author zhangwei
 *
 */
@SuppressWarnings("all")
public class BaseController {
	
	Logger LOG = LoggerFactory.getLogger(BaseController.class);
	//配置属性
	@Value(value="${base_url}")
	protected String base_url;
	@Value(value="${base_static_url}")
	protected String base_static_url;
	@Value(value="${vmsuffix}")
	protected String vmSuffix;
	
	//基础属性
	@Resource protected VelocityConfigurer velocityCfg;
	@Resource private AuthorContext authorLocal;
	
	/**
	 * 获取Velocity模板引擎
	 * @return
	 */
	private final VelocityEngine getVmEngine(){
		return velocityCfg.getVelocityEngine();
	}
	
	/**
	 * 根据VM配置文件的key获取配置属性值
	 * @param key
	 * @return
	 */
	protected final String getVmProperty(String key){
		
		if(StringUtils.isEmpty(key)){
			return "";
		}
		
		VelocityEngine engine = getVmEngine();
		if(engine == null){
			return "";
		}
		
		return (String)engine.getProperty(key);
	}
	/**
	 * 获取VM的输入编码格式,默认为UTF-8
	 * @return
	 */
	protected final String getVmInputEncoding(){
		Object encoding = getVmProperty("input.encoding");
		return (String)(encoding==null?Constants.VM_ENCODING_DEFAULT:encoding);
	}
	
	/**
	 * 获取VM的输出编码格式,默认为UTF-8
	 * @return
	 */
	protected final String getVmOutPutEncoding(){
		Object encoding = getVmProperty("output.encoding");
		return (String)(encoding==null?Constants.VM_ENCODING_DEFAULT:encoding);
	}
	
	/**
	 * 获取Vm默认数据对象
	 * @return
	 */
	protected final Map<String, Object> getVmContext(){
		
		
		Map<String,Object> context = new HashMap<String, Object>();
		context.put(Constants.BASE_URL_NAME, base_url);
		context.put(Constants.BASE_STATIC_URL_NAME, base_static_url);
		context.put("tool", VelocityTools.class);
		context.put("v", Constants.static_v);
		context.put("user", authorLocal.getLocal().get());
		return context;
	}
	
	/**
	 * 将Map数据对象转换成Vm Context对象
	 * @param data
	 * @return
	 */
	protected final VelocityContext map2Context(Map data){
		if(null == data){
			return null;
		}
		return new VelocityContext(data);
	}
	
	/**
	 * 返回console(控制台) vm数据
	 * @param view 要输出的模板试图文件名称
	 * @param data 模板解析需要的数据 Map格式
	 * @param skip true:跳过默认的布局,false:使用默认的布局
	 * @return
	 * @throws Exception
	 */
	protected final ModelAndView toVmConsole(String view,Map data) throws Exception{
		
		return toVm(view, data, false,Constants.VM_LAYOUT_CONSOLE);
	}
	
	/**
	 * 返回 index(首页) vm数据
	 * @param view
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected final ModelAndView toVmIndex(String view,Map data) throws Exception{
		
		return toVm(view, data, false, Constants.VM_LAYOUT_INDEX);
	}
	
	/**
	 * 返回试图<br>
	 * <li> 该方式返回的试图默认跳过layout,只返回基础元素
	 * @param view vm文件名称
	 * @param data 模板解析需要的数据
	 * @return
	 * @throws Exception
	 */
	protected final ModelAndView toVm(String view,Map data) throws Exception{
		
		return toVm(view, data, true, null);	 	
	}
	
	/**
	 * 输出模板数据
	 * @param view 要输出的模板试图
	 * @param data 模板需要的数据对象
	 * @param skip true:跳过默认的布局,false:使用默认的布局
	 * @param layoutvm: 模板VM在velocity配置中的key
	 * @return
	 * @throws Exception
	 */
	private final ModelAndView toVm(String view,Map data,boolean skip,String layoutvm) throws Exception{
		LOG.info("开始输出模板 {}",new String[]{view});
		
		VelocityEngine engine = getVmEngine();
		Map context = getVmContext();
		ModelAndView viewModel = null;
		
		if(null == data){
			data =new HashMap<String, Object>();
		}
		
		//判断模板是否存在
		if(!engine.resourceExists(view+vmSuffix)){
			LOG.error("velocity模板{}不存在,请检查模板配置",new String[]{view});
			throw new Exception("velocity模板{"+view+"} 不存在,请检查模板配置");
		}
		context.putAll((Map)data);
		
		if(skip){//跳过模板
			LOG.info("输出模板 {} 时跳过默认布局",new String[]{view});
			return new ModelAndView(view,context);
		}
		
		Template bodyTmp = engine.getTemplate(view+vmSuffix, getVmInputEncoding());
		//整合试图内容到body
		StringWriter bodyContent = new StringWriter();
		bodyTmp.merge(map2Context(context), bodyContent);
		
		//将内容以参数的形式添加到layout
		context.put(Constants.VM_LAYOUT_CONTENT_KEY, bodyContent.toString());
		
		String layout = (String) engine.getProperty(layoutvm);
		
		viewModel = new ModelAndView(layout,context);
		
		return viewModel;
	}
	
	/**
	 * 返回JSON数据
	 * @param res
	 * @param data 数据内容
	 * @param flag 成功或失败
	 * @param msg 提示信息
	 * @return
	 * @throws Exception
	 */
	public final ModelAndView toJson(HttpServletResponse res,Map data,boolean flag,String msg) throws Exception{
		PrintWriter out = res.getWriter();
		if(null ==data){
			data =new HashMap<String, Object>();
		}
		Map context = data;
		context.put(Constants.RESPONSE_JSON_STATUS_KEY, flag?Constants.RESPONSE_JSON_STATUS_OK:Constants.RESPONSE_JSON_STATUS_FAIL);
		context.put(Constants.RESPONSE_JSON_MSG_KEY, msg);
		
		out.print(JSONObject.toJSONString(context));
		
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 判断请求是否要求返还json格式
	 * @param req
	 * @throws Exception
	 */
	protected final boolean isJsonRequest(HttpServletRequest req) throws Exception{
		
		if("json".equals(req.getHeader("response-type"))){//json请求
			return true;
		}else if ("json".equals(req.getAttribute("response-type"))){
			return true;
		}
		return false;
	}
	
	protected final Page convertPage(HttpServletRequest req) {
		Page page = null;
		try {
			page = new Page(Integer.parseInt(req.getParameter("currentPage")));
		} catch (Exception e) {
			page = new Page(1);
		}
		return page;
	}
}
