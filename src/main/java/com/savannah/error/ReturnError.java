package com.savannah.error;

/**
 * 装饰者模式
 * 抽象组件
 * @author stalern
 * @date 2019/12/11~09:09
 */
public interface ReturnError {
    /**
     * 得到错误代码
     * @return ErrCode
     */
    int getErrCode();

    /**
     * 得到错误信息
     * @return ErrMsg
     */
    String getErrMsg();

    /**
     * 设置错误信息
     * @param errMsg 错误信息
     * @return Error实体
     */
    ReturnError setErrMsg(String errMsg);
}
