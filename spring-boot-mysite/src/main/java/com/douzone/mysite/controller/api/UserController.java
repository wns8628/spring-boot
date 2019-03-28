package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller("userApiController") //스캔할떄 UserController가 두개니깐 에러남  그래서 아디를부여함
@RequestMapping("/user/api")
public class UserController { 

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkEmail(@RequestParam(value="email", required=true, defaultValue="") String email ){
		
		UserVo vo = userService.existEmail(email);
		//이메일존재여부확인 userService.existEmail*() 	//이거할떄 쿼리문에러나면(ajax통신중) 서버쪽에서 에러난거인데
		//json받아야하는데 html에러페이지를 받아버림 
		boolean exist = vo != null;
		
			return JSONResult.success(exist);
	}
}
