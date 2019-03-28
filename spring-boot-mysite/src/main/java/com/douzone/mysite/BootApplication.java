package com.douzone.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootConfiguration
//@EnableAutoConfiguration //라이브러리 추측 스캔해서 자동설정다해줌?
//@ComponentScan("com.douzone.mysite")
@SpringBootApplication //패키지이름을 com.douozne.hellospring 해야지 밑에애들 쭈르륵스캔
public class BootApplication {	
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
