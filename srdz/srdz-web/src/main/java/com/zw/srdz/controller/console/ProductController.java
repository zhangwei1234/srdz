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
import com.zw.srdz.domain.Page;
import com.zw.srdz.domain.Product;
import com.zw.srdz.service.ProductService;
import com.zw.srdz.service.cache.CacheManager;

@Controller
@RequestMapping(value="console")
@Author(type={AuthorType.LOGIN_USER})
public class ProductController extends BaseController{

	@Resource private ProductService productService;
	
	@RequestMapping(value="/product", method={RequestMethod.POST})
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		Map<String, Object> data = Maps.newHashMap();
		data.put("groups", CacheManager.getInstance().getGroups());
		data.put("page", new Page(1));
		return toVm("console/product/product", data);
	}
	
	@RequestMapping(value="/product/list", method={RequestMethod.POST})
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) throws Exception{
		int typeId = 0;
		try {
			typeId = Integer.parseInt(req.getParameter("typeId"));
		} catch (Exception e) {
			typeId = -1;
		}
		Page page = convertPage(req);
		Map<String, Object> data = Maps.newHashMap();
		page = productService.listProduct(typeId, page);
		data.put("page", page);
		
		return toVm("console/product/product_list", data);
	}
	
	@RequestMapping(value="/product/loadNew", method={RequestMethod.POST})
	public ModelAndView loadNew(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		return toVm("console/product/product_add", productService.loadNew());
	}
	
	@RequestMapping(value="/product/save", method={RequestMethod.POST})
	public ModelAndView save(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Product product) throws Exception{
		
		String types = req.getParameter("type_ids");
		
		boolean flag = productService.addProduct(product, types);
		
		if(flag){
			return toJson(res, null, flag, "商品添加成功");
		}
		return toJson(res, null, flag, "商品添加失败");
	}
	
	@RequestMapping(value="/product/{id}/loadUpdate", method={RequestMethod.POST})
	public ModelAndView loadUpdate(HttpServletRequest req, HttpServletResponse res,@PathVariable int id) throws Exception{
		return toVm("console/product/product_update", productService.loadUpdate(id));
	}
	
	@RequestMapping(value="/product/update", method={RequestMethod.POST})
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res, @ModelAttribute Product product) throws Exception{
		String types = req.getParameter("type_ids");
		
		boolean flag = productService.updateProduct(product, types);
		
		if(flag){
			return toJson(res, null, flag, "商品修改成功");
		}
		return toJson(res, null, flag, "商品修改失败");
	}
	
	@RequestMapping(value="/product/{ids}", method={RequestMethod.DELETE})
	public ModelAndView remove(HttpServletRequest req, HttpServletResponse res, @PathVariable String ids) throws Exception{
		
		boolean flag = productService.removeProduct(ids);
		if(flag){
			return toJson(res, null, flag, "商品数据删除成功");
		}
		return toJson(res, null, flag, "商品数据删除失败");
	}
}
