package com.wqh.tdd.args;

import com.wqh.tdd.args.exceptions.IllegalValueException;
import com.wqh.tdd.args.exceptions.InsufficientException;
import com.wqh.tdd.args.exceptions.TooManyArgumentsException;
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
            // setup
            OptionParser<Boolean> bool = OptionParsers.bool();
            // exercise
            Boolean result = bool.parse(Arrays.asList(), option("l"));
            // verify
            assertFalse(result);
            // teardown

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

        // TODO illegal value format
        @Test
        public void should_raise_exception_illegal_value_format() {
            Function<String, Object> parse = it -> {
                throw new RuntimeException();
            };
            Object defaultValue = new Object();
            IllegalValueException e = assertThrows(IllegalValueException.class,
                    () -> OptionParsers.unary(parse, defaultValue).parse(Arrays.asList("-p", "8080"), option("p")));
            assertEquals("p", e.getParameter());
            assertEquals("8080", e.getValue());
        }
    }


    @Nested
    class ListOptionParserTest {
        //TODO: -g "this" "is" => {"this", is"}
        @Test
        public void should_parse_list_value() {
            assertArrayEquals(new String[]{"this", "is"}, OptionParsers.list(String[]::new, String::valueOf).parse(Arrays.asList("-g", "this", "is"), option("g")));
        }

        //TODO: default value => []
        @Test
        public void should_use_empty_array_if_no_parameter() {
            assertArrayEquals(new String[]{}, OptionParsers.list(String[]::new, String::valueOf).parse(Arrays.asList(), option("g")));
        }

        //TODO: -d a => throw exception
        @Test
        public void should_throw_exception_if_value_parser_cant_parse_value() {
            Function<String, Integer> parser = it -> {
                throw new RuntimeException();
            };
            IllegalValueException e = assertThrows(IllegalValueException.class, () -> OptionParsers.list(Integer[]::new, parser).parse(Arrays.asList("-d", "a"), option("d")));
            assertEquals("d", e.getParameter());
            assertEquals("a", e.getValue());
        }

        @Test
        public void should_not_treat_negative_int_as_flag() {
            assertArrayEquals(new Integer[]{-1, -2}, OptionParsers.list(Integer[]::new, Integer::valueOf).parse(Arrays.asList("-d",  "-1", "-2"), option("d")));
        }
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
