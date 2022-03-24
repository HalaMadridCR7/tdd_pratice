package com.wqh.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        return PARSER.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    static Map<Class<?>, OptionParser> PARSER = Map.of(
            Boolean.class, new BooleanOptionParser(),
            Integer.class, new SingleValueOptionParser<Integer>(Integer::valueOf, 0),
            String.class, new SingleValueOptionParser<String>(String::valueOf, "")
    );


}
