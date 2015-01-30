package com.zw.srdz.controller.console;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Maps;
import com.zw.srdz.author.Author;
import com.zw.srdz.author.AuthorType;
import com.zw.srdz.base.BaseController;
import com.zw.srdz.domain.Group;
import com.zw.srdz.service.GroupService;

/**
* 项目名称：srdz-web   
* 类名称：GroupController   
* 类描述：   商品分组管理
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-1-30 下午2:08:16   
* 修改人：zhangwei
* 修改时间：2015-1-30 下午2:08:16   
* 修改备注：   
* @version    
*
 */
@Controller
@Author(type={AuthorType.LOGIN_USER})
@RequestMapping(value="/console/group")
public class GroupController extends BaseController{

	@Resource private GroupService groupService;
	
	@RequestMapping(value="/load", method={RequestMethod.POST})
	public ModelAndView group(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		List<Group> groups = groupService.listGroups();
		Map<String, Object> data = Maps.newHashMap();
		data.put("groups", groups);
		
		return toVm("console/group/group", data);
	}
	
	@RequestMapping(value="/list", method={RequestMethod.POST})
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) throws Exception{
		List<Group> groups = groupService.listGroups();
		Map<String, Object> data = Maps.newHashMap();
		data.put("groups", groups);
		
		return toVm("console/group/group_list", data);
	}
	
	@RequestMapping(value="/loadNew", method={RequestMethod.POST})
	public ModelAndView loadNew(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return toVm("console/group/group_add", null);
	}
	
	@RequestMapping(value="/add", method={RequestMethod.POST})
	public ModelAndView add(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Group group) throws Exception{
		
		boolean flag = groupService.addGroup(group);
		if (flag){
			return toJson(res, null, flag, "分组添加成功");
		}
		return toJson(res, null, flag, "分组添加失败");
	}
	
	/**
	 * 删除分组
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/remove", method={RequestMethod.POST})
	public ModelAndView remove(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String groupId = req.getParameter("groupId");
		
		boolean flag = groupService.removeGroup(groupId);
		if (flag){
			return toJson(res, null, flag, "分组删除成功");
		}
		return toJson(res, null, flag, "分组删除失败(分组中可能还存在分类信息)"); 
	}
	
	@RequestMapping(value="/loadUpdate", method={RequestMethod.POST})
	public ModelAndView loadUpdate(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		String groupId = req.getParameter("groupId");
		Group group = groupService.getGroup(groupId);
		Map<String, Object> data = Maps.newHashMap();
		data.put("group", group);
		
		return toVm("console/group/group_update", data);
	}
	
	@RequestMapping(value="update", method={RequestMethod.POST})
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Group group) throws Exception{
		boolean flag = groupService.updateGroup(group);
		if (flag){
			return toJson(res, null, flag, "分组修改成功");
		}
		return toJson(res, null, flag, "分组修改失败"); 
	}
	
	/**
	 * 分组升序或降序
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ascTdesc", method={RequestMethod.POST})
	public ModelAndView descTAsc(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		String ascId = req.getParameter("ascId");
		String descId = req.getParameter("descId");
		
		boolean flag = groupService.ascTDesc(ascId, descId);
		if(flag){
			return toJson(res, null, flag, "分组排序成功");
		}
		return toJson(res, null, flag, "分组排序失败"); 
	}
}
