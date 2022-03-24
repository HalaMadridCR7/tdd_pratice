package com.wqh.args;

/**
 * @author wqh
 * @date 2022-03-24 06:57
 */
public class InsufficientException extends RuntimeException{

    private String option;

    public InsufficientException(String message) {
        super(message);
        this.option = message;
    }

    public String getOption() {
        return option;
    }
}

