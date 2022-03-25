package com.wqh.args;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

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
        int index = arguments.indexOf("-" + option.value());

        if(index == -1) {
            return defaultValue;
        }

        List<String> values = values(arguments, index);
        if(values.size() < 1) {
            throw new InsufficientException(option.value());
        }
        if(values.size() > 1) {
            throw new TooManyArgumentsException(option.value());
        }
        String value = values.get(0);
        return parseValue(value);
    }

    public static List<String> values(List<String> arguments, int i) {
        return arguments.subList(i + 1, IntStream.range(i + 1, arguments.size()).filter(rangeId -> arguments.get(rangeId).startsWith("-")).findFirst().orElse(arguments.size()));
    }

    protected T parseValue(String value) {
        return PARSER.apply(value);
    }

}
