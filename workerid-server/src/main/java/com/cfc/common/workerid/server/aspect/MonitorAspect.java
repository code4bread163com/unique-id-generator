package com.cfc.common.workerid.server.aspect;


import com.cfc.common.idcommon.enums.ErrorCodeEnum;
import com.cfc.common.idcommon.utils.JacksonUtils;
import com.cfc.common.workerid.api.TransOutput;
import com.cfc.common.workerid.server.annotation.MonitorAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 监控切面
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Slf4j
@Aspect
@Order(-99)
@Component
public class MonitorAspect {
    @Around("@annotation(monitorAnnotation)")
    public Object aroundProcess(ProceedingJoinPoint proceedingJoinPoint,
                                MonitorAnnotation monitorAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String url = null;
        String method = null;
        String address = null;
        if (attributes != null) {
            HttpServletRequest requestParam = attributes.getRequest();
            method = requestParam.getMethod();
            url = requestParam.getRequestURL() != null ? requestParam.getRequestURL().toString() : null;
            address = requestParam.getRemoteAddr();
        }

        // 请求入参
        Object[] requestArgs = proceedingJoinPoint.getArgs();
        // 返回参数
        Object responseArgs = null;

        try {
            responseArgs = proceedingJoinPoint.proceed(requestArgs);
            return responseArgs;
        } catch (Exception e) {
            log.error("URL={}", url, e);
            return new TransOutput<>(ErrorCodeEnum.SERVICE_ERROR.getCode(), ErrorCodeEnum.SERVICE_ERROR.getText(), null);
        } finally {

            // 若需要打印入参和返回结果
            if (monitorAnnotation.needLog()) {
                try {
                    long spend = System.currentTimeMillis() - startTime;

                    log.info("【{}】【URL={}】【IP={}】【REQUEST={}】【RESPONSE={}】【TIME={} ms】",
                            method, url, address,
                            JacksonUtils.objectToJson(requestArgs),
                            JacksonUtils.objectToJson(responseArgs),
                            spend);
                } catch (Exception e) {
                    log.error("打印入参和返回结果时发生异常，url={}", url, e);
                }
            }
        }
    }
}