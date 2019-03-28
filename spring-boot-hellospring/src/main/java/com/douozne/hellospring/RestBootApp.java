package com.douozne.hellospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootConfiguration
@EnableAutoConfiguration //라이브러리 추측 스캔해서 자동설정다해줌?
@ComponentScan("com.douzone.hellospring")
public class RestBootApp {

	@RestController
	public class MyController{
		
		@Autowired
		MyService myService;
		
		@GetMapping("/")
		public String hello() {
			return myService.hello();
		}
	}
		
	@Component
	public class MyService{
		public String hello() {
			return "Hello world";
		}
	}
//	
//	public static void main(String[] args) {
//		SpringApplication.run(BootApplication.class, args);
//	}
}
