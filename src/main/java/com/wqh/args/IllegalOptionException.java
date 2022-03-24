package com.wqh.args;

/**
 * @author wqh
 * @date 2022-03-24 23:37
 */
public class IllegalOptionException extends RuntimeException{

    private String parameter;

    public IllegalOptionException(String parameter) {
        super(parameter);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
