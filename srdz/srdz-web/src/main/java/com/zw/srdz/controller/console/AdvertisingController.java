package com.zw.srdz.controller.console;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Maps;
import com.zw.srdz.author.Author;
import com.zw.srdz.author.AuthorType;
import com.zw.srdz.base.BaseController;
import com.zw.srdz.common.util.DateUtil;
import com.zw.srdz.domain.Advertising;
import com.zw.srdz.service.AdvertisingService;

@Controller
@Author(type={AuthorType.LOGIN_USER})
@RequestMapping(value="console")
public class AdvertisingController extends BaseController{

	@Resource private AdvertisingService advertisingService;
	
	@RequestMapping(value="/advertising", method={RequestMethod.POST})
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("advertisings", advertisingService.list());
		
		return toVm("console/advertising/advertising", data);
	}
	
	
	@RequestMapping(value="/advertising/loadNew", method={RequestMethod.POST})
	public ModelAndView loadNew(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		return toVm("console/advertising/advertising_add", null);
	}
	
	@RequestMapping(value="/advertising/{id}/loadUpdate", method={RequestMethod.POST})
	public ModelAndView loadUpdate(HttpServletRequest req, HttpServletResponse res, @PathVariable int id) throws Exception{
		Map<String, Object> data = Maps.newHashMap();
		data.put("advertising", advertisingService.get(id));
		return toVm("console/advertising/advertising_update", data);
	}
	
	@RequestMapping(value="/advertising/add", method={RequestMethod.POST})
	public ModelAndView save(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Advertising advertising) throws Exception{
		advertising.setStartTime(DateUtil.dateFormatyMd(req.getParameter("startTime1")));
		advertising.setEndTime(DateUtil.dateFormatyMd(req.getParameter("endTime1")));
		boolean flag = advertisingService.add(advertising);
		
		if (flag){
			return toJson(res, null, flag, "广告添加成功");
		}
		return toJson(res, null, flag, "广告添加失败");
	}
	
	@RequestMapping(value="/advertising/update", method={RequestMethod.POST})
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Advertising advertising) throws Exception{
		advertising.setStartTime(DateUtil.dateFormatyMd(req.getParameter("startTime1")));
		advertising.setEndTime(DateUtil.dateFormatyMd(req.getParameter("endTime1")));
		boolean flag = advertisingService.update(advertising);
		
		if (flag){
			return toJson(res, null, flag, "广告修改成功");
		}
		return toJson(res, null, flag, "广告修改失败");
	}
	
	@RequestMapping(value="/advertising/{ids}", method={RequestMethod.DELETE})
	public ModelAndView del(HttpServletRequest req, HttpServletResponse res, @PathVariable String ids) throws Exception{
		boolean flag = advertisingService.batchDel(ids);
		
		if (flag){
			return toJson(res, null, flag, "批量删除广告成功");
		}
		return toJson(res, null, flag, "批量删除广告失败");
	}
	
	@RequestMapping(value="/advertising/{ids}/active", method={RequestMethod.PUT})
	public ModelAndView active(HttpServletRequest req, HttpServletResponse res, @PathVariable String ids) throws Exception{
		boolean flag = advertisingService.batchActive(ids);
		
		if (flag){
			return toJson(res, null, flag, "批量启用广告成功");
		}
		return toJson(res, null, flag, "批量启用广告失败");
	}
	
	@RequestMapping(value="/advertising/{ids}/disabled", method={RequestMethod.PUT})
	public ModelAndView disabled(HttpServletRequest req, HttpServletResponse res, @PathVariable String ids) throws Exception{
		boolean flag = advertisingService.batchDisabled(ids);
		
		if (flag){
			return toJson(res, null, flag, "批量禁用广告成功");
		}
		return toJson(res, null, flag, "批量禁用广告失败");
	}
	
}
