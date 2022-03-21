package com.wqh.args;

/**
 * @author wqh
 * @date 2022-03-21 07:15
 */
public class TooManyArgumentsException extends RuntimeException{

    private String option;

    public TooManyArgumentsException(String message) {
        super(message);
        this.option = message;
    }

    public String getOption() {
        return option;
    }
}
