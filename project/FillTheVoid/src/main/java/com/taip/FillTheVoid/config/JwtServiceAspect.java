package com.taip.FillTheVoid.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
@Component
public class JwtServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(JwtServiceAspect.class);

    @Pointcut("execution(* com.taip.FillTheVoid.config.proxy.JwtServiceProxy.*(..))")
    public void jwtServiceMethods() {}

    @Before("jwtServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("AOP: Executând metoda: " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("AOP: Argument: " + arg);
        }
    }
    @Around("execution(* com.taip.FillTheVoid.config.proxy.JwtServiceProxy.isTokenValid(..)) && args(jwt, userDetails)")
    public Object validateParameters(ProceedingJoinPoint joinPoint, String jwt, UserDetails userDetails) throws Throwable {
        if (jwt == null || userDetails == null) {
            logger.warn("AOP: Parametrii invalizi în isTokenValid: jwt sau userDetails sunt null");
            return false;
        }
        return joinPoint.proceed();
    }

    // Loghează după ce metoda a fost executată
    @AfterReturning(pointcut = "jwtServiceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("AOP: Metoda " + joinPoint.getSignature().getName() + " a returnat: " + result);
    }
}