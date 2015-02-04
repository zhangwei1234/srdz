package com.zw.srdz.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Maps;
import com.zw.srdz.author.Author;
import com.zw.srdz.author.AuthorType;
import com.zw.srdz.base.BaseController;
import com.zw.srdz.domain.Advertising;
import com.zw.srdz.domain.Type;
import com.zw.srdz.service.HomeService;
import com.zw.srdz.service.cache.CacheManager;

@Controller
@Author(type={AuthorType.LOGIN_USER_NOT})
@RequestMapping(value="/")
public class IndexController extends BaseController{

	@Resource private HomeService homeService;
	
	@RequestMapping(method={RequestMethod.GET})
	public ModelAndView index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Map<String, Object> data = Maps.newHashMap();
		
		data.put("groups", CacheManager.getInstance().getGroups());
		data.put("advertisings", CacheManager.getInstance().getAdvertisingList(Advertising.LOCATION_HOME));
		return toVmIndex("index/index", data);
	}
	
	/**
	 * 响应点击分组的数据
	 * @param req
	 * @param res
	 * @param group
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/group/{group}", method={RequestMethod.GET})
	public ModelAndView group(HttpServletRequest req, HttpServletResponse res, @PathVariable int group) throws Exception{
		List<Type> types = CacheManager.getInstance().getTypes(group);
		if(null == types || types.size() ==0){
			return toVmIndex("index/default", null);
		}
		Map<String, Object> data = homeService.loadGroup(group, types);
		if("list".equals(data.get("display"))){
			return toVmIndex("index/group_list", data);
		}else{
			return toVmIndex("index/group", data);
		}
		
	}
	
	@RequestMapping(value="/type/list/{type}/{display}/{start}/{ordertype}", method={RequestMethod.POST})
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res,@PathVariable int start,
			@PathVariable int type, @PathVariable int display, @PathVariable int ordertype) throws Exception{
		
		Map<String, Object> data = homeService.loadList(type, display, ordertype, start);
		
		if(Type.DISPLAY_ICON == display){
			return toJson(res, data, true, "");
		}else{
			return toVm("index/list", data);
		}
	}
	
	@RequestMapping(value="/product/click/{id}", method={RequestMethod.PUT})
	public ModelAndView clickProduct(HttpServletRequest req, HttpServletResponse res,@PathVariable int id) throws Exception{
		homeService.clickProduct(id);
		return toJson(res, null, true, "");
	}
	
	@RequestMapping(value="/product/parise/{id}", method={RequestMethod.PUT})
	public ModelAndView pariseProduct(HttpServletRequest req, HttpServletResponse res, @PathVariable int id) throws Exception{
		homeService.pariseProduct(id);
		return toJson(res, null, true, "");
	}
	
}
