package com.zw.srdz.controller.console;

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
@RequestMapping(value="console")
@Author(type={AuthorType.LOGIN_USER})
public class ConsoleController extends BaseController{

	/**
	 * 控制台首页
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method={RequestMethod.GET})
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return toVmConsole("console/home", null);
	}
}
