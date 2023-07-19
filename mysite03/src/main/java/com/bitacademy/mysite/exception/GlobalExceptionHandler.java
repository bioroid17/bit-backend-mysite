package com.bitacademy.mysite.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Container가 이 어노테이션을 알아본다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// Exception.class: 모든 Exception을 받을 것
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception ex) {
		// 1. 로깅
		ex.printStackTrace();	// 지금은 로깅을 안 배워서 화면에 출력
		
		// 2. 사과
		return "error/exception";
	}
}
