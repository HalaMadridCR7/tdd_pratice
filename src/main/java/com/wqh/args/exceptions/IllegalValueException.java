package com.wqh.args.exceptions;

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

}
