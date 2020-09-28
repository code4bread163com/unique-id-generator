package com.cfc.common.workerid.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 异常处理
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransOutputAnnotation {
}
