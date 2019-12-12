package com.savannah.controller;

import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stalern
 * @date 2019/12/11~16:59
 */
public class BaseController {

    /**
     * 定义exceptionHandler解决未被controller层吸收的exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>(2);
        if( ex instanceof ReturnException){
            ReturnException businessException = (ReturnException)ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{
            responseData.put("errCode", EmReturnError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmReturnError.UNKNOWN_ERROR.getErrMsg());
        }
        return ReturnType.create(responseData,"fail");

    }
}
