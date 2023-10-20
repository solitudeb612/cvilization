package cn.swust.indigo.admin.aspect;

import cn.swust.indigo.admin.entity.po.SysLog;
import cn.swust.indigo.admin.service.SysLogService;
import cn.swust.indigo.common.log.annotation.Log;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 操作日志
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class SysLogAspect {
    private final SysLogService logService;

    @Around(value = "@annotation(cn.swust.indigo.common.log.annotation.Log)")
    public Object around(ProceedingJoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //IP地址
        String ipAddr = getRemoteHost(request);
        String url = request.getRequestURL().toString();
        SysLog log = new SysLog();
        log.setRemoteAddr(ipAddr);
        log.setMethod(request.getMethod());
        log.setRequestUri(url);
        log.setCreateTime(LocalDateTime.now());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Log logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(Log.class);
        try {
            log.setTitle(logAnnotation.value());
            Long startTime = System.currentTimeMillis();
            Object obj = joinPoint.proceed();
            Long endTime = System.currentTimeMillis();
            log.setTime(endTime - startTime);
            logService.save(log);
            return obj;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.setException(throwable.getMessage());
            logService.save(log);
        }
        return null;
    }

    /**
     * 获取目标主机的ip
     *
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}

