package com.wqh.tdd.args.exceptions;

/**
 * @author wqh
 * @date 2022-03-25 23:02
 */
public class IllegalValueException extends RuntimeException {

    private String parameter;

    private String value;

    public IllegalValueException(String parameter, String value) {
        this.parameter = parameter;
        this.value = value;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
