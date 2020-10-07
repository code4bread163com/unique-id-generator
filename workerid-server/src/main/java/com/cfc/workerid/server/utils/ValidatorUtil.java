package com.cfc.workerid.server.utils;

import com.cfc.uid.common.enums.ErrorCodeEnum;
import com.cfc.uid.common.exception.UidGenerateException;
import com.cfc.workerid.api.TransInput;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * validator工具类
 *
 * @author zhangliang
 * @date 2020/9/23
 */
public class ValidatorUtil {
    private ValidatorUtil() {
        throw new UidGenerateException("工具类无需实例化");
    }

    private static Validator validator;

    static {
        //  实例化
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    /**
     * 校验包含组信息的注解
     *
     * @param obj    待验证的实体
     * @param groups 校验组别
     * @param <T>    待验证的类型
     */
    public static <T> void validate(T obj, Class<?>... groups) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, groups);
        assembleErrorMsg(set);
    }

    /**
     * 校验方法
     *
     * @param t   待验证的实体
     * @param <T> 待验证的类型
     */
    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> set = validator.validate(t);
        assembleErrorMsg(set);
    }

    /**
     * 判空校验
     *
     * @param requestAo 入参
     */
    public static <T> void validateBasicAndBusiParam(TransInput<T> requestAo) {
        // 1.基本入参判空校验
        ValidatorUtil.validate(requestAo);

        // 2.业务入参判空校验
        ValidatorUtil.validate(requestAo.getData());
    }

    private static <T> void assembleErrorMsg(Set<ConstraintViolation<T>> set) {
        if (!CollectionUtils.isEmpty(set)) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> error : set) {
                validateError.append(String.format("%s ：[%s = %s] ;", error.getMessage(), error.getPropertyPath(), error.getInvalidValue()));
            }
            throw new UidGenerateException(ErrorCodeEnum.INVALID_PARAM.getCode(), validateError.toString());
        }
    }
}
