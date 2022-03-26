package com.wqh.tdd.args;

import com.wqh.args.Option;
import com.wqh.args.OptionParser;
import com.wqh.args.exceptions.InsufficientException;
import com.wqh.args.OptionParsers;
import com.wqh.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wqh
 * @date 2022-03-23 07:09
 */
public class OptionParsersTest {

    @Nested
    class BooleanOptionParserTest {

        // sad path
        @Test
        public void shop_not_accept_extra_arguments_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.bool().parse(Arrays.asList("-l", "t"), option("l"));
            });
            assertEquals("l", e.getOption());
        }

        // default value
        @Test
        public void shop_set_default_value_to_false_if_option_not_present() {
            Boolean result = OptionParsers.bool().parse(Arrays.asList(), option("l"));
            assertFalse(result);

        }

        // happ path
        @Test
        public void should_set_boolean_option_to_true_if_flag_present() {
            Boolean result = OptionParsers.bool().parse(Arrays.asList("-l"), option("l"));
            assertTrue(result);
        }
    }


    @Nested
    class UnaryOptionParser {
        // sad path
        @Test
        public void should_not_accept_extra_arguments_for_single_value_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.unary(Integer::valueOf, 0).parse(Arrays.asList("-p", "8080", "8081"), option("p"));
            });
            assertEquals("p", e.getOption());
        }

        // sad path
        @ParameterizedTest
        @ValueSource(strings = {"-p -l", "-p"})
        public void should_not_accept_insufficient_arguments_for_single_value_option(String arguments) {
            InsufficientException e = assertThrows(InsufficientException.class, () -> {
                OptionParsers.unary(Integer::valueOf, 0).parse(Arrays.asList(arguments.split(" ")), option("p"));
            });

            assertEquals("p", e.getOption());
        }

        // default value
        @Test
        public void should_set_default_value_to_for_single_value_option() {
            Function<String, Object> whatever = it -> null;
            Object defaultValue =  new Object();
            assertSame(defaultValue, OptionParsers.unary(whatever, defaultValue).parse(Arrays.asList(), option("p")));
        }


        // happy path
        @Test
        public void should_parse_value_if_flag_present() {
            Object parsed =  new Object();
            Function<String, Object> parse = it -> parsed;
            Object defaultValue = new Object();
            assertSame(parsed, OptionParsers.unary(parse, defaultValue).parse(Arrays.asList("-p", "8080"), option("p")));
        }
    }


    @Nested
    class ListOptionParserTest {
        //TODO: -g "this" "is" => {"this", is"}
        @Test
        public void shop_parse_list_value() {
            String[] result = OptionParsers.list(String[]::new, String::valueOf).parse(Arrays.asList("-g", "this", "is"), option("g"));
            assertArrayEquals(result, new String[]{"this", "is"});
        }

        //TODO: default value => []
        //TODO: -d a => throw exception
    }

    static Option option(String value) {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
