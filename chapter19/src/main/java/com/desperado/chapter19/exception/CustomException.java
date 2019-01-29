package com.desperado.chapter19.exception;

/**
 * 定义个自己的异常，看看如何捕获…
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 4564124491192825748L;

    private int code;

    public CustomException() {
        super();
    }

    public CustomException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
