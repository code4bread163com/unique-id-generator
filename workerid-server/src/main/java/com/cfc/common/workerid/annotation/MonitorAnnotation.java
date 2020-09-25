package com.cfc.common.workerid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务监控注解
 *
 * @author zhangxiaojun
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorAnnotation {
    /**
     * 被监控的名字
     *
     * @return String
     */
    String name();

    /**
     * 是否需要打印接口的入参和出参日志，默认不打印
     *
     * @return boolean
     */
    boolean needLog() default true;

}
