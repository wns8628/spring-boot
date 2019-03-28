package com.douzone.hellospring.controller;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@ResponseBody
	// @RequestMapping : 메서드 단독 매핑 :메서드에만붙였잔아 
	@RequestMapping({"/main",""})
	public String main() {
		return "MainController:main()";
	}
	
	@ResponseBody
	@RequestMapping("/main2/a/b/c/d")
	public String main2() {
		return "MainController:main2()";
	}
	
	
	/*
	 * 비추천= 기술침투
	 * 	*/
	@ResponseBody
	@RequestMapping("/main3")
	public String main3(HttpServletRequest request, Writer out) {
		String name = request.getParameter("n"); //이따구로하면 다른데서 이 컨트롤러 못쓰지 
		return "MainController:main3()";
	}
}
