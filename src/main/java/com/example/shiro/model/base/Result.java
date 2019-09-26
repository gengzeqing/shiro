package com.example.shiro.model.base;

import com.example.shiro.common.enums.RetCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Result<T> extends BaseDTO {
    private static final long serialVersionUID = -175830694466731423L;

    /**
     * 指定状态码和提示信息的构造函数
     * @param code      状态码
     * @param message   提示信息
     */
    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回指定状态码枚举的结果对象
     * @param retCode 指定错误码枚举
     */
    public static <T> Result<T> retCode(RetCodeEnum retCode) {
        Result<T> response = new Result<>();
        response.setCode(retCode.getCode());
        response.setMessage(retCode.getMessage());
        return response;
    }

    /**
     * 返回业务意义上成功状态的结果对象
     */
    public static <T> Result<T> success() {
        return retCode(RetCodeEnum.SUCCESS);
    }

    /**
     * 返回成功状态的带业务数据内容的结果对象
     * @param body 业务数据内容
     */
    public static <T> Result<T> success(T body) {
        Result<T> response = retCode(RetCodeEnum.SUCCESS);
        response.setData(body);
        return response;
    }

    /**
     * 返回{@linkplain RetCodeEnum#SYSTEM_ERROR 系统错误}状态的结果对象
     */
    public static <T> Result<T> error() {
        Result<T> response = new Result<>();
        response.setCode(RetCodeEnum.SYSTEM_ERROR.getCode());
        response.setMessage(RetCodeEnum.SYSTEM_ERROR.getMessage());
        return response;
    }

    /**
     * 返回{@linkplain RetCodeEnum#FAILED 通用失败}并带特定错误提示信息的结果对象
     * @param message 特定错误提示信息
     */
    public static <T> Result<T> failed(String message) {
        Result<T> response = new Result<>();
        response.setCode(RetCodeEnum.FAILED.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 失败
     * @param code      错误码
     * @param message   错误描述
     */
    public static <T> Result<T> error(String code, String message) {
        Result<T> response = new Result<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /**
     * 返回失败结果
     * @param code      错误码
     * @param message   错误描述
     * @param data      结果对象
     */
    public static <T> Result<T> error(String code, String message, T data) {
        Result<T> response = error(code, message) ;
        response.setData(data);
        return response;
    }

    /**
     * 返回处理中状态的结果对象
     */
    public static <T> Result<T> processing() {
        return retCode(RetCodeEnum.PROCESSING);
    }

    /**
     * 返回参数校验失败的结果对象
     */
    public static <T> Result<T> validateFailed() {
        return retCode(RetCodeEnum.VALIDATE_FAILED);
    }

    /**
     * 返回参数校验失败状态并设置独立提示信息的结果对象
     */
    public static <T> Result<T> validateFailed(String message) {
        Result<T> response = new Result<>();
        response.setCode(RetCodeEnum.VALIDATE_FAILED.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 判断当前结果是否业务意义上成功了
     * @return true-成功，false-失败
     */
    public boolean successed() {
        return code != null && RetCodeEnum.SUCCESS.getCode().equals(code);
    }

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 外部返回错误码
     */
    private String extCode ;
    /**
     * 外部返回错误描述信息
     */
    private String extMessage ;
    /**
     * 返回信息
     */
    private T data;

}
