package com.wqh.args;

import com.wqh.args.exceptions.IllegalValueException;
import com.wqh.args.exceptions.InsufficientException;
import com.wqh.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;
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

    public static OptionParser<Boolean> bool() {
        // 这个就是通过lambda表达式的方式， 实例化了一个OptionParser
        return (arguments, option) -> {
            return values(arguments, option, 0).map(it -> true).orElse(false);
        };
    }


    @Override
    public T parse(List<String> arguments, Option option) {
        return values(arguments, option, 1).map(it -> parseValue(option, it.get(0))).orElse(defaultValue);
    }

    public static Optional<List<String>> values(List<String> arguments, Option option, int exceptedSize) {
        int index = arguments.indexOf("-" + option.value());
        if(index == -1) {
            return Optional.empty();
        }

        List<String> values = values(arguments, index);
        if(values.size() < exceptedSize) {
            throw new InsufficientException(option.value());
        }
        if(values.size() > exceptedSize) {
            throw new TooManyArgumentsException(option.value());
        }
        return Optional.of(values);
    }

    private T parseValue(Option option, String value) {
        try {
            return parseValue(value);
        } catch (Exception e) {
            throw new IllegalValueException(option.value(), value);
        }
    }

    public static List<String> values(List<String> arguments, int i) {
        return arguments.subList(i + 1, IntStream.range(i + 1, arguments.size()).filter(rangeId -> arguments.get(rangeId).startsWith("-")).findFirst().orElse(arguments.size()));
    }

    protected T parseValue(String value) {
        return PARSER.apply(value);
    }

}
