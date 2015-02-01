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
import com.zw.srdz.domain.Type;
import com.zw.srdz.service.GroupService;
import com.zw.srdz.service.TypeService;
import com.zw.srdz.service.cache.CacheManager;

/**
 * 商品分类控制
 * @author wei
 *
 */
@Controller
@RequestMapping(value="console")
@Author(type={AuthorType.LOGIN_USER})
public class TypeController extends BaseController{

	@Resource private TypeService typeService;
	@Resource private GroupService groupService;
	
	@RequestMapping(value="/type", method={RequestMethod.POST})
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		return toVm("console/type/type", typeService.loadIndex(0));
	}
	
	@RequestMapping(value="/type/{groupId}", method={RequestMethod.POST})
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res,@PathVariable int groupId) throws Exception{
		
		return toVm("console/type/type", typeService.loadIndex(groupId));
	}
	
	@RequestMapping(value="/type/loadNew", method={RequestMethod.POST})
	public ModelAndView loadNew(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("groups", groupService.listGroups());
		
		return toVm("console/type/type_add", data);
	}
	
	@RequestMapping(value="/type/add", method={RequestMethod.POST})
	public ModelAndView add(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Type type) throws Exception{
		
		boolean flag = typeService.addType(type);
		if (flag){
			return toJson(res, null, flag, "分类信息添加成功");
		}
		
		return toJson(res, null, flag, "分类信息添加失败");
	} 
	
	@RequestMapping(value="/type/{typeId}", method={RequestMethod.DELETE})
	public ModelAndView remove(HttpServletRequest req, HttpServletResponse res, @PathVariable int typeId) throws Exception{
		boolean flag = typeService.removeType(typeId);
		if (flag){
			return toJson(res, null, flag, "分类信息删除成功");
		}
		
		return toJson(res, null, flag, "分类信息删除失败");
	}
	
	@RequestMapping(value="/type/{typeId}/loadUpdate", method={RequestMethod.POST})
	public ModelAndView loadUpdate(HttpServletRequest req, HttpServletResponse res, @PathVariable int typeId) throws Exception{
		
		return toVm("console/type/type_update", typeService.loadUpdate(typeId));
	}
	
	@RequestMapping(value="/type/update", method={RequestMethod.POST})
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Type type) throws Exception{
		
		boolean flag = typeService.updateType(type);
		if(flag){
			return toJson(res, null, flag, "分类修改成功");
		}
		
		return toJson(res, null, flag, "分类信息修改失败");
	} 
	
	@RequestMapping(value="/type/{ascId}/{descId}", method={RequestMethod.PUT})
	public ModelAndView descTAsc(HttpServletRequest req, HttpServletResponse res, @PathVariable int ascId, @PathVariable int descId) throws Exception{
		
		boolean flag = typeService.ascTdescType(ascId, descId);
		if(flag){
			return toJson(res, null, flag, "分类排序成功");
		}
		return toJson(res, null, flag, "分类排序失败"); 
	}
	
	@RequestMapping(value="/type/{groupId}", method={RequestMethod.GET})
	public ModelAndView loadType(HttpServletRequest req, HttpServletResponse res, @PathVariable int groupId) throws Exception{
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("types", CacheManager.getInstance().getTypes(groupId));
		return toJson(res, data, true, "");
	} 
}
