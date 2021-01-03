package com.oak.gateway.common.response;

/**
 * @author: chippy
 * @datetime 2020-12-31 13:13
 */
public class Result<T> implements com.chippy.core.common.response.Result<T> {

    private int code;
    private String errorMsg;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> fail(int code, String errorMsg) {
        return new Result<>(code, errorMsg);
    }

    @Override
    public int definitionSuccessCode() {
        return 0;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @SuppressWarnings("unchecked")
    private Result() {
        this.code = this.definitionSuccessCode();
        this.data = (T)Boolean.TRUE;
        this.errorMsg = null;
    }

    private Result(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = null;
    }

    private Result(T data) {
        this.code = this.definitionSuccessCode();
        this.data = data;
        this.errorMsg = null;
    }

}
