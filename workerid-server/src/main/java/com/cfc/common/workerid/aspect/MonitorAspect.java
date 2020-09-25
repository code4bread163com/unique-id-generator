package com.cfc.common.workerid.aspect;


import com.alibaba.fastjson.JSON;
import com.cfc.common.workerid.annotation.MonitorAnnotation;
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
        HttpServletRequest requestParam = attributes.getRequest();

        // 请求入参
        Object[] requestArgs = proceedingJoinPoint.getArgs();
        // 返回参数
        Object responseArgs = null;

        try {
            responseArgs = proceedingJoinPoint.proceed(requestArgs);
            return responseArgs;
        } catch (Exception e) {
            log.error("URL:{},Args={}",
                    requestParam.getRequestURL().toString(),
                    Arrays.toString(requestArgs), e);
            throw e;
        } finally {
            // 若需要打印入参和返回结果
            if (monitorAnnotation.needLog()) {
                // 打印入参和返回结果
                this.printRequestAndResponseLog(monitorAnnotation.name(), requestArgs, responseArgs);
            }

            long spend = System.currentTimeMillis() - startTime;
            if (log.isInfoEnabled()) {
                // 记录下请求内容
                log.info("[IP={}][{}] URL:{};[{}][{}.{}], SPEND TIME : {} ms",
                        requestParam.getRemoteAddr(),
                        monitorAnnotation.name(),
                        requestParam.getRequestURL().toString(),
                        requestParam.getMethod(),
                        proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                        proceedingJoinPoint.getSignature().getName(),
                        spend);
            }
        }
    }

    /**
     * 打印入参和返回结果
     *
     * @param serviceName  当前接口服务名
     * @param requestArgs  请求参数
     * @param responseArgs 返回结果
     */
    private void printRequestAndResponseLog(String serviceName,
                                            Object[] requestArgs,
                                            Object responseArgs) {
        try {
            log.info("{}入参: {}, 返回结果: {}",
                    serviceName,
                    JSON.toJSONString(requestArgs),
                    JSON.toJSONString(responseArgs));
        } catch (Exception e) {
            log.error("{}打印入参和返回结果时发生异常", serviceName, e);
        }
    }
}
