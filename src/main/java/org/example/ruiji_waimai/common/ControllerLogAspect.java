package org.example.ruiji_waimai.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    /**
     * controller aspect
     * 定义切入点
     */
    @Pointcut("execution(* org.example.ruiji_waimai.controller.*.*(..))")
    public void controllerAspect() {

    }

    @Around(value = "controllerAspect()")
    public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        // 请求URL
        String requestURL = httpServletRequest.getRequestURL().toString();
        log.info("requestURL:{}, reqeustParams:{}", requestURL, Arrays.toString(point.getArgs()));

        // 开始时间
        Long startTime = System.currentTimeMillis();
        try {
            Object response = point.proceed(); // 获取返参
            // 结束时间
            Long endTime = System.currentTimeMillis();
            // 打印日志
            log.info("response:{}, spentTime:{}", null == response ? "" : JSONObject.toJSONString(response),
                    endTime - startTime);
            return response;
        } catch (Exception e) {
            // 打印异常信息
            log.error("response error, error message:{}", e.getMessage());
            throw e;
        }
    }

}
