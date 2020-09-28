package com.cfc.common.workerid.server.advice;

import com.cfc.common.idcommon.enums.ErrorCodeEnum;
import com.cfc.common.workerid.server.annotation.TransOutputAnnotation;
import com.cfc.common.workerid.api.TransOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常拦截
 *
 * @author zhangliang
 * @date 2020/9/23
 */
@ControllerAdvice(annotations = TransOutputAnnotation.class)
@Slf4j
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TransOutput exceptionHandler(Exception e, HttpServletRequest request) {
        log.error("控制层接口抛出异常，URL={}", request.getRequestURI(), e);
        return new TransOutput(ErrorCodeEnum.SERVICE_ERROR.getCode(), ErrorCodeEnum.SERVICE_ERROR.getText(), null);
    }
}
