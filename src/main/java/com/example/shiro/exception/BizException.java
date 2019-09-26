package com.example.shiro.exception;


public class BizException extends RuntimeException {

    private String code;
    private String msg;

    public BizException() {
        super();
    }

    public BizException(String msg) {
        super(msg);
        this.code = "201";
        this.msg = msg;
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
