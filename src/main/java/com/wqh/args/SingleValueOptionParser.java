package com.wqh.args;

import java.util.List;
import java.util.function.Function;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
public class SingleValueOptionParser<T> implements OptionParser<T> {

    Function<String, T> PARSER;

    T defaultValue;

    public SingleValueOptionParser(Function<String, T> PARSER, T defaultValue) {
        this.PARSER = PARSER;
        this.defaultValue = defaultValue;
    }


    @Override
    public T parse(List<String> arguments, Option option) {
        int i = arguments.indexOf("-" + option.value());

        if(i == -1) {
            return defaultValue;
        }

        if(i + 1 == arguments.size() || arguments.get(i + 1).startsWith("-")) {
            throw new InsufficientException(option.value());
        }

        if(i + 2 < arguments.size()
                &&  !arguments.get(i + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }

        String value = arguments.get(i + 1);
        return parseValue(value);
    }

    protected T parseValue(String value) {
        return PARSER.apply(value);
    }
}
