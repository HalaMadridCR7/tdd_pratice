package com.wqh.args;

import java.util.List;
import java.util.function.Function;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
public class SingleValueOptionParser<T> implements OptionParser<T> {

    Function<String, T> PARSER;

    public SingleValueOptionParser(Function<String, T> PARSER) {
        this.PARSER = PARSER;
    }

    @Override
    public Object parse(List<String> arguments, Option option) {
        int i = arguments.indexOf("-" + option.value());
        String value = arguments.get(i + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {
        return PARSER.apply(value);
    }
}
