package com.wqh.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * @author wqh
 * @date 2022-03-19 07:36
 */
public class Args {

    public static<T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(parameter -> parseOption(parameter, arguments)).toArray();
            return (T)constructor.newInstance(values);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(Parameter parameter, List<String> arguments) {
        Object value = null;
        Option option = parameter.getAnnotation(Option.class);
        if(parameter.getType() == Boolean.class) {
            value =  parseBoolean(arguments, option);
        }

        if(parameter.getType() == Integer.class) {
            value = parseInt(arguments, option);
        }

        if(parameter.getType() == String.class) {
            value = parseString(arguments, option);
        }
        return value;
    }


    interface OptionParser {
        Object parse(List<String> arguments, Option option);
    }

    private static Object parseString(List<String> arguments, Option option) {
        return new StringOptionParser().parse(arguments, option);
    }

    static class StringOptionParser implements OptionParser{

        @Override
        public Object parse(List<String> arguments, Option option) {
            int i = arguments.indexOf("-" + option.value());
            return arguments.get(i + 1);
        }
    }


    private static Object parseInt(List<String> arguments, Option option) {
        return new IntOptionParser().parse(arguments, option);
    }

    static class IntOptionParser implements OptionParser{

        @Override
        public Object parse(List<String> arguments, Option option) {
            int i = arguments.indexOf("-" + option.value());
            return Integer.valueOf(arguments.get(i + 1));
        }
    }

    private static Object parseBoolean(List<String> arguments, Option option) {
        return new BooleanOptionParser().parse(arguments, option);
    }

    static class BooleanOptionParser implements OptionParser{
        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains("-" + option.value());
        }
    }
}
