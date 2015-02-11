package com.zw.srdz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zw.srdz.author.Author;
import com.zw.srdz.author.AuthorType;
import com.zw.srdz.base.BaseController;

/**
* 项目名称：srdz-web   
* 类名称：CustomerController   
* 类描述：   客户中心
* 创建人：zhangwei
* 邮箱:  zhangwei_2943@163.com
* 创建时间：2015-2-11 下午1:47:01   
* 修改人：zhangwei
* 修改时间：2015-2-11 下午1:47:01   
* 修改备注：   
* @version    
*
 */

@Controller
@RequestMapping(value="/cus")
@Author(type={AuthorType.LOGIN_CUSTOMER})
public class CustomerController extends BaseController{

	@RequestMapping(value="/center", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView center(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return null;
	}
}
