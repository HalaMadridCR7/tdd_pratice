package com.wqh.tdd.args.exceptions;

/**
 * @author wqh
 * @date 2022-03-27 23:04
 */
public class UnSupportedOptionTypeException extends RuntimeException {

    private String value;

    private Class<?> type;

    public UnSupportedOptionTypeException(String value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
