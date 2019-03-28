package com.douozne.emaillist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration //라이브러리 추측 스캔해서 자동설정다해줌?
@ComponentScan("com.douzone.emaillist")
//@SpringBootApplication //패키지이름을 com.douozne.hellospring 해야지 밑에애들 쭈르륵스캔
public class BootApplication {	
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
