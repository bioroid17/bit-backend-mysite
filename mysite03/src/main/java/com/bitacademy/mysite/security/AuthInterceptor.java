package com.bitacademy.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bitacademy.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// assets가 들어오면 미스캐스팅 가능성이 있지만, 설정 차원에서 assets는 매핑에서 제외한 상태
		// 하지만 이미지가 꼭 assets를 통해서만 들어온다는 보장은 없으므로 안전을 위해 아래 코드를 짠다.
		
		// 1. handler 종류 확인
		if (!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Handler의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. @Auth가 없으면
		if (auth == null) {
			return true;
		}
		
		// 5. 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// 인증이 안 된 경우
		if (authUser == null) {
			// 여기는 Controller가 아니므로 Context path를 전부 써줘야 한다.
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;	// Handler가 실행 못 되게 막는다
		}
		
		// 인증됨!
		return true;
	}
}
