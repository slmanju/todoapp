package com.manjula.todo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("within(com.manjula.todo.service..*)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String args = Arrays.toString(joinPoint.getArgs());
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            String message = className + "." + methodName + "() : " + elapsedTime + "ms";
            if (logger.isDebugEnabled()) {
                logger.debug(message);
            } else {
                logger.info(message);
            }
            return result;
        } catch (Exception e) {
            logger.error("Exception " + e.getMessage() + " in " + className + "." + methodName + "(" + args + ")");
            throw e;
        }
    }

}
