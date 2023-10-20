package cn.swust.indigo.common.core.exception;

import cn.swust.indigo.common.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

/**
 * 异常处理
 */

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger log = LoggerFactory.getLogger("docLogger");

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R badRequestException(IllegalArgumentException exception) {
        log.error("请求异常" + exception.getMessage());
        return R.failed(exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R badRequestException(AccessDeniedException exception) {
        log.error("请求异常" + exception.getMessage());
        return R.failed(exception.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
            UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R badRequestException(Exception exception) {
        return R.failed(exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Throwable throwable) {
        log.error("系统异常" + throwable.getMessage());
        return R.failed(throwable.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R violationException(HttpServletRequest req, ConstraintViolationException throwable) {
        String errorMsg = "";
        for (ConstraintViolation<?> constraintViolation : throwable.getConstraintViolations()) {

            errorMsg += constraintViolation.getPropertyPath().toString() + ":"
                    + constraintViolation.getMessage() + ";";
        }
        log.error("数据验证错误" + throwable.getMessage());
        return R.failed(errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R bindException(HttpServletRequest req, MethodArgumentNotValidException throwable) {
        log.error("数据验证错误" + throwable.getMessage());
        String errorMsg = "";

        for (FieldError error : throwable.getBindingResult().getFieldErrors()) {
            errorMsg += error.getField() + ":"
                    + error.getDefaultMessage() + ";";
        }
        return R.failed(errorMsg);
    }
}
