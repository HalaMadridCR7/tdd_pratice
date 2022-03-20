package com.wqh.argstdd;

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
            value = arguments.contains("-" + option.value());
        }

        if(parameter.getType() == Integer.class) {
            int i = arguments.indexOf("-" + option.value());
            value = Integer.valueOf(arguments.get(i + 1));
        }

        if(parameter.getType() == String.class) {
            int i = arguments.indexOf("-" + option.value());
            value = arguments.get(i + 1);
        }
        return value;
    }
}
