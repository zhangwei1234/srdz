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

@Controller
@Author(type={AuthorType.LOGIN_USER_NOT})
@RequestMapping(value="/")
public class IndexController extends BaseController{

	@RequestMapping(method={RequestMethod.GET})
	public ModelAndView index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		
		return toVmIndex("index/index", null);
	}
}
