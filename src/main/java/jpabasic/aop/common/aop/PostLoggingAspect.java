package jpabasic.aop.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class PostLoggingAspect {

	// pointcut 은 AOP 에서 공통 관심 기능을 적용할 메서드를 지정하는 역할을 합니다.
	@Pointcut("execution(* jpabasic.aop.service.*.*(..))") // <- 경로를 설정해 줍니다 해석해 보자면 서비스 패키지 내의 모든 클래스의 모든 메서드에 적용
	public void postServiceMethods() {}

	// 메서드를 실행하기 전 로깅을 진행합니다.
	@Before("postServiceMethods()")
	public void beforeMethodExecution() {
		log.info("요청이 들어왔습니다: {}", PostContextHolder.getPostInfo());
	}

	// 메서드가 정상 실행 된 후 서비스 메서드의 반환 결과를 result 에 담아서 로깅합니다.
	@AfterReturning(value = "postServiceMethods()", returning = "result")
	public void afterReturning(Object result) {
		log.info("응답 반환: {}", result);
	}

	// 만약 게시글 서비스의 메서드가 실행 중 오류가 발생했을 시 실행됩니다.
	@AfterThrowing(value = "postServiceMethods()", throwing = "ex")
	public void afterThrowing(Exception ex) {
		log.error("에러 발생: {}", ex.getMessage());
		PostContextHolder.clear();
	}

	// 로깅을 진행할 경로를 설정해주기 위해 사용하는 어노테이션 @Around
	@Around("execution(* jpabasic.aop.service.PostService.*(..))")
	// ProceedingJoinPoint 는 실행중인 메서드(JoinPoint)에 접근할 수 있도록 도와주는 객체이다.
	public Object logPost(ProceedingJoinPoint pjp)throws Throwable {
		try{
			// 로그를 남기기 위해 메서드를 실행 하는 로직입니다. result 에 메서드 실행 결과값이 담깁니다.
			Object result = pjp.proceed();
			// (컨텍스트홀더에 저장된 데이터 값을 가져옵니다.)
			String postInfo = PostContextHolder.getPostInfo();
			// 게시글의 내용이 비어있지 않다면 로그를 남기는 로직입니다.
			if (postInfo != null) {
				log.info("::: 로깅 데이터: {}", postInfo);
			}
			//반환된 값을 반환해 줍니다.
			return result;

		} finally {
			// 마지막으로 컨텍스트홀더(전역 공간)에 저장된 결과값을 삭제해 줍니다.
			PostContextHolder.clear();
		}
	}
}
