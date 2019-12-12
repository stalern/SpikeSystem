package com.savannah.error;

/**
 * 装饰者模式异常实现
 * 作为具体装饰者，目的是为了重新设置EmReturnError类的ErrMsg
 * @author stalern
 * @date 2019/12/11~09:22
 */
public class ReturnException extends Exception implements ReturnError {

    private ReturnError returnError;

    /**
     * 直接接收EmBusinessError的传参用于构造业务异常
     * @param returnError 错误类型
     */
    public ReturnException(ReturnError returnError) {
        super();
        this.returnError = returnError;
    }

    /**
     * 接收自定义errMsg的方式构造业务异常
     * @param returnError 错误类型
     * @param errMsg 错误信息
     */
    public ReturnException(ReturnError returnError,String errMsg){
        super();
        this.returnError = returnError;
        this.returnError.setErrMsg(errMsg);
        printStackTrace();
    }

    @Override
    public int getErrCode() {
        return this.returnError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.returnError.getErrMsg();
    }

    @Override
    public ReturnError setErrMsg(String errMsg) {
        this.returnError.setErrMsg(errMsg);
        return this;
    }
}
