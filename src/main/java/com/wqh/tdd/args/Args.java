package com.wqh.tdd.args;

import com.wqh.tdd.args.exceptions.IllegalOptionException;
import com.wqh.tdd.args.exceptions.UnSupportedOptionTypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wqh
 * @date 2022-03-19 07:36
 */
public class Args<T> {

    private Class<T> optionsClass;
    private Map<Class<?>, OptionParser> parsers;

    public Args(Class<T> optionsClass, Map<Class<?>, OptionParser> parser) {
        this.optionsClass = optionsClass;
        this.parsers = parser;
    }

    public static<T> T parse(Class<T> optionsClass, String... args) {
        return new Args<T>(optionsClass, PARSER).parse(args);
    }

    public T parse(String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(parameter -> parserOption(parameter, arguments)).toArray();
            return (T) constructor.newInstance(values);
        } catch (IllegalOptionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object parserOption(Parameter parameter, List<String> arguments) {
        if(!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptionException(parameter.getName());
        }
        Option option = parameter.getAnnotation(Option.class);
        if(!parsers.containsKey(parameter.getType())) {
            throw new UnSupportedOptionTypeException(option.value(), parameter.getType());
        }
        return parsers.get(parameter.getType()).parse(arguments, option);
    }

    static Map<Class<?>, OptionParser> PARSER = Map.of(
            Boolean.class, OptionParsers.bool(),
            Integer.class, OptionParsers.unary(Integer::valueOf, 0),
            String.class, OptionParsers.unary(String::valueOf, ""),
            String[].class, OptionParsers.list(String[]::new, String::valueOf),
            Integer[].class, OptionParsers.list(Integer[]::new, Integer::valueOf)
    );


}
