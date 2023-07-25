package com.bitacademy.mysite.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitacademy.mysite.security.AuthInterceptor;
import com.bitacademy.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.bitacademy.mysite.security.LoginInterceptor;
import com.bitacademy.mysite.security.LogoutInterceptor;

@Configuration
public class SecurityConfig implements WebMvcConfigurer{
	// Argument Resolver
	@Bean
	public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(handlerMethodArgumentResolver());
	}
	
	// Interceptor
	// Interceptor는 자동 설정이 안된다.
	@Bean
	public HandlerInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}

	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// Java config에서는 패턴에 해당되는 핸들러가 있어야 한다.
		// XML은 없어도 OK
		// 따라서 UserController.java에 auth와 logout을 처리할 매핑을 가라로 만들었다.
		registry
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");
		
		registry
			.addInterceptor(logoutInterceptor())
			.addPathPatterns("/user/logout");
		
		registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/assets/**", "/user/auth", "/user/logout");
	}
}
