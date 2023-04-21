package com.sky.aspect;

import com.alibaba.druid.support.http.util.IPAddress;
import com.alibaba.fastjson.JSONObject;
import com.sky.context.BaseContext;
import com.sky.mapper.OperationlogMapper;
import com.sky.utils.IPAddressUtils;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class OperationDetect {
    @Autowired
    OperationlogMapper operationlogMapper;

    @Around("@annotation(com.sky.annotation.OperationLog)")
    public Object operationDetect(ProceedingJoinPoint joinPoint) throws Throwable {
        Long userId = BaseContext.getCurrentId();
        String exceptionDetected=null;
        Object proceed=null;
        LocalDateTime updateTime = LocalDateTime.now();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String[] split = url.split("/");
        int portCheck = split[3].equals("admin")?1:2;
        HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取请求用户IP

        String ip = IPAddressUtils.getIpAdrress(request1);
        if (ip == null) {
            return "error/limit";
        }
        String method = joinPoint.getSignature().getName();
        String args = JSONObject.toJSONString(joinPoint.getArgs());
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable ex) {
            exceptionDetected = ex.getMessage();
            ex.printStackTrace();
        }finally {
            operationlogMapper.insert(url,portCheck,ip,method,exceptionDetected,args,userId,updateTime);
        }
        return proceed;

    }
}
