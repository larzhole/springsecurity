package com.example.demo.utilities.logging;

import com.example.demo.utilities.annotations.Loggable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Aspect
@Configuration
public class LoggingAspect {

    @Pointcut("@annotation(com.example.demo.utilities.annotations.Loggable)")
    public void loggableMethod() {
        // Method is empty, this is just a Pointcut, implementations are in the Advices
    }

    @Around("loggableMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        final boolean shouldLogPayload = ((MethodSignature) joinPoint.getSignature()).getMethod()
                .getAnnotation(Loggable.class).shouldLogPayload();

        // Log before entering method
        final LogMessage logMessage = new LogMessage(
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                request.getLocalAddr(),
                request.getRemoteAddr(),
                getResourceName(joinPoint),
                LogMessage.STATUS.ENTER,
                shouldLogPayload ? joinPoint.getArgs() : null,
                null
        );

        String message = new ObjectMapper().writeValueAsString(logMessage);
        log.info(message);

        // Execute method
        final Object result = joinPoint.proceed();

        // Log after method
        logMessage.setTimestamp(System.currentTimeMillis());
        logMessage.setStatus(LogMessage.STATUS.EXIT);
        logMessage.setArguments(null);
        logMessage.setResult(result.toString());
        message = new ObjectMapper().writeValueAsString(logMessage);
        log.info(message);

        return result;
    }

    @AfterThrowing(pointcut = "loggableMethod()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws JsonProcessingException  {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        final LogMessage logMessage = new LogMessage(
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                request.getLocalAddr(),
                request.getRemoteAddr(),
                getResourceName(joinPoint),
                LogMessage.STATUS.ERROR,
                null,
                null
        );
        final String message = new ObjectMapper().writeValueAsString(logMessage);
        log.error(message, e);
    }

    private String getResourceName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() +
                "." +
                joinPoint.getSignature().getName() +
                "()";
    }
}
