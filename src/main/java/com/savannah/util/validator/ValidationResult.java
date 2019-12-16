package com.savannah.util.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 校验结果
 * @author stalern
 * @date 2019/12/15~19:12
 */
public class ValidationResult {
    private boolean hasErrors = false;

    private Map<String,String> errorMsgMap = new HashMap<>(10);

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * 通过格式化字符串信息来获得所有msg
     * @return 字符串msg
     */
    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}
