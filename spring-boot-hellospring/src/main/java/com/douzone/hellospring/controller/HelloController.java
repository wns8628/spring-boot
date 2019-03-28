package com.douzone.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	//@ResponseBody = 바로 나옴 스트링으로 . / 리턴인데 외부로나오네? 예전엔 pw 이런거 써서 해야하는데 
	@RequestMapping("/hello") //요청을 이 메소드로 매핑한다.  "/hellospring/hello  hellospring는 톰캣에있잔아거기서 접근하니 /hello로만 따면되지 "
	public String hello() {
		return "hello"; //
	}
}
