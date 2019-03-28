package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {

	@Autowired
	private SiteService siteService;
	
	@RequestMapping({ "","/main"} )
	public String main(Model model) {
		SiteVo siteVo = siteService.main();
		model.addAttribute("siteVo",siteVo);
		return "main/index"; //뷰리졸브 설정
	}
	
	@ResponseBody
	@RequestMapping( "/hello" )
	public String hello() {
		return "<h1>안녕하세요</h1>";
	}
	
	@ResponseBody
	@RequestMapping( "/hello2" )
	public JSONResult hello2() {
		JSONResult jsonResult = JSONResult.success(new UserVo());
		
		return jsonResult;		
	}
}
