package lu.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import lu.springboot.common.ResponseResult;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DaoYunException.class)
    public Object handleLogMonitorException(HttpServletResponse response, DaoYunException e) throws IOException {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = e.getErrorCode();
        return ResponseResult.newFailedResult(errorCode.getCode(), errorCode.getMsg());
    }
}
