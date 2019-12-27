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
    USER_AUTH_DENIES(10004,"用户权限不够"),
    /**
     * 20000开头为商品相关
     */
    ITEM_NOT_EXIT(20001,"商品不存在"),
    ITEM_CAN_NOT_CREATE(20002,"商品不能被创建"),
    STOCK_NOT_ENOUGH(20003,"库存不够"),
    ITEM_DELETE_ERROR(20003,"商品删除失败"),

    /**
     * 30000开头为活动相关
     */
    PROMO_EXIST_ERROR(30001, "活动出现异常"),
    PROMO_NOT_EXIT(30002, "活动不存在");


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
