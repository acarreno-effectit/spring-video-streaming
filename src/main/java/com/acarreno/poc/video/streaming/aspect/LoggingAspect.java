package com.acarreno.poc.video.streaming.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Pointcut("execution(* com.acarreno.poc.video.streaming.service.*.*(..))")
  public void pointcut() {}

  @Before("pointcut()")
  public void logBeforeMethod(JoinPoint joinPoint) {
    log.debug("Init {} class=[{}] method=[{}], args=[{}]", Thread.currentThread().getId(),
        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(pointcut = "pointcut()", returning = "result")
  public void logAfterMethod(JoinPoint joinPoint, Object result) {
    log.debug("Result {} class=[{}] method=[{}], result=[{}]", Thread.currentThread().getId(),
        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
        result.toString());
  }
}
