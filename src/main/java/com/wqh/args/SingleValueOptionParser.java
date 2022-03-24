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
        int i = arguments.indexOf("-" + option.value());

        if(i == -1) {
            return defaultValue;
        }

        List<String> values = values(arguments, i);
        if(values.size() < 1) {
            throw new InsufficientException(option.value());
        }
        if(values.size() > 1) {
            throw new TooManyArgumentsException(option.value());
        }
        String value = arguments.get(i + 1);
        return parseValue(value);
    }

    private List<String> values(List<String> arguments, int i) {
        int followingFlag = IntStream.range(i + 1, arguments.size()).filter(rangeId -> arguments.get(rangeId).startsWith("-")).findFirst().orElse(arguments.size());
        return arguments.subList(i + 1, followingFlag);
    }

    protected T parseValue(String value) {
        return PARSER.apply(value);
    }

    public static void main(String[] args) {
        List<String> testList = Arrays.asList("a", "-l", "a", "b");
        int x = 2;
        int i = IntStream.range(x, testList.size()).filter(index -> testList.get(index).startsWith("-")).findFirst().orElse(testList.size() - x);
        System.out.println(i);


    }
}
