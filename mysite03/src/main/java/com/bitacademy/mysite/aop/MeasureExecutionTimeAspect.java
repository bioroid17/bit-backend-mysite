package com.bitacademy.mysite.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureExecutionTimeAspect {
	// ||로 AOP를 적용할 패키지, 메소드 등을 여럿 지정할 수 있다.
	@Around("execution(* *..*.repository.*.*(..)) || execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// before
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		// after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;
		
		System.out.println("[Execution Time]["+taskName+"]"+totalTime+"millis");
		
		return result;
	}
}