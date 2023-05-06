package me.cuiyijie.nongmo.config;

/**
 * @author cyj976655@gmail.com
 * @date 2021/3/13 11:47
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 这样写是将重复的代码提取出来方便处理
     */
    @Pointcut("execution(public * me.cuiyijie.nongmo.controller.MainController.*(..))")
    public void log() {
    }

    /**
     * @param joinPoint 注意：该方法中的HttpServletRequest为javax.servlet.http.HttpServletRequest;
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            //记录请求路径url,记录请求方式method,记录访问者ip,记录访问的类方法,记录传递的参数
            logger.info("url={},method={},ip={},class_method={},args={}",
                    request.getRequestURL(),
                    request.getMethod(),
                    request.getRemoteAddr(),
                    joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                    joinPoint.getArgs()
            );
        }
    }

    @After("log()")
    public void doAfter() {
    }

    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfterReturning(Object obj) {
        logger.info("response={}", obj);
    }
}
