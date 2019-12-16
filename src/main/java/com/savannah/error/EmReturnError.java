package com.savannah.error;

/**
 * 装饰者模式
 * 作为具体构件
 * @author stalern
 * @date 2019/12/11~09:12
 */
public enum EmReturnError implements ReturnError {

    /**
     * 通用错误类型 99999
     */
    PARAMETER_VALIDATION_ERROR(99999, "参数不合法"),
    /**
     * 未知错误 99998
     */
    UNKNOWN_ERROR(99998, "未知异常"),
    /**
     * 10000开头为用户相关信息错误定义
      */
    USER_NOT_EXIST(10001, "用户不存在"),
    USER_LOGIN_FAIL(10002,"用户登录失败"),
    USER_NOT_LOGIN(10003,"用户未登陆"),
    USER_AUTH_DENIES(10004,"用户权限不够");


    private int errCode;
    private String errMsg;

    EmReturnError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public ReturnError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
