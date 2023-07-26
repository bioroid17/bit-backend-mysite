package com.bitacademy.mysite.exception;

public class UserRepositoryException extends RuntimeException{
	// RuntimeException: 반드시 try-catch로 처리할 필요 없는 예외
	// 별도의 설정 없으면 모든 RuntimeException이 계속 올라가서 DispatcherServlet에서 모여서 처리된다.
	// 만약 CheckedException을 썼으면 일일이 try-catch로 처리해야 했을 것이다.
	// Error 처리는
		// 1. 사과 페이지
		// 2. 로그 남기기 <- 이건 못하고 있음
		// 3. 정상 종료
	// 지금 2번(로그 남기기)를 못하고 있으니 각 Controller에서 예외 처리를 할 수 있게 설정해야 한다.
		// 내 애플리케이션인데 DispatcherServlet에게 예외 처리를 시킬 이유가 없다.
	// 근데 그것도 싫으니 AOP를 쓴다.
		// 하나의 코드로 모든 애플리케이션 RuntimeException을 처리할 수 있게 하는게 목표
		// 예외 처리를 여러 곳에서 하지 않을 것
	
	public UserRepositoryException(String message) {
		super(message);
	}
}
