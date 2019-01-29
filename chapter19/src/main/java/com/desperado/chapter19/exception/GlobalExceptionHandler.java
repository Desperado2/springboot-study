package com.desperado.chapter19.exception;

import com.desperado.chapter19.exception.CustomException;
import com.desperado.chapter19.exception.ErrorResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * @ControllerAdvice 捕获 Controller 层抛出的异常，如果添加 @ResponseBody 返回信息则为JSON 格式。
 * @RestControllerAdvice 相当于 @ControllerAdvice 与 @ResponseBody 的结合体。
 * @ExceptionHandler 统一处理一种类的异常，减少代码重复率，降低复杂度。
 * 创建一个 GlobalExceptionHandler 类，并添加上 @RestControllerAdvice 注解就可以定义出异常通知类了，然后在定义的方法中添加上 @ExceptionHandler 即可实现异常的捕捉…
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 定义要捕获的异常 可以多个 @ExceptionHandler({})
     * @param request
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ErrorResponseEntity customExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response){
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        CustomException customException = (CustomException) e;
        return new ErrorResponseEntity(customException.getCode(),customException.getMessage());
    }

    /**
     * 捕获  RuntimeException 异常
     * TODO  如果你觉得在一个 exceptionHandler 通过  if (e instanceof xxxException) 太麻烦
     * TODO  那么你还可以自己写多个不同的 exceptionHandler 处理不同异常
     *
     * @param request  request
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response){
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        RuntimeException customException = (RuntimeException) e;
        return new ErrorResponseEntity(400,customException.getMessage());
    }

    /**
     * 通用的接口映射异常处理方
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(new  ErrorResponseEntity(status.value(),argumentNotValidException.getBindingResult().getAllErrors()
            .get(0).getDefaultMessage()),status);
        }
        if(ex instanceof MethodArgumentTypeMismatchException){
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
                    + ",信息：" + exception.getLocalizedMessage());
            return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
        }
        return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
    }
}
