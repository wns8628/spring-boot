package com.douzone.mysite.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class GlobalExceptionHandler {

	// 글로벌이지
	@AfterThrowing(value = "execution(* *..*.*.*(..))", throwing = "ex") // 포인트컷지정
	public void afterThrowingAdvice(Throwable ex) {
		System.out.println("call [afterThrowing advice]" + ex);
	}

}
