package com.savannah.response;

/**
 * 往前端返回的请求结果
 * @author stalern
 * @date 2019/12/11~08:34
 */
public class ReturnType {
    /**
     * 表明对应请求的返回处理结果 "success" 或 "fail"
     */
    private String status;

    /**
     * 若status=success,则data内返回前端需要的json数据
     * 若status=fail，则data内使用通用的错误码格式
     */
    private Object data;

    /**
     * 定义一个通用的创建方法，用于返回值为空的情况
     */
    public static ReturnType create() {
        return ReturnType.create("ok");
    }

    public static ReturnType create(Object result){
        return ReturnType.create(result,"success");
    }

    public static ReturnType create(Object result,String status){
        ReturnType type = new ReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
