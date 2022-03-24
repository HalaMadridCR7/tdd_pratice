package com.wqh.tdd.args;

import com.wqh.args.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author wqh
 * @date 2022-03-23 07:09
 */
public class SingleValueOptionParserTest {

    // sad path
    @Test
    public void should_not_accept_extra_arguments_for_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<Integer>(Integer::valueOf, 0).parse(Arrays.asList("-p", "8080", "8081"), option("p"));
        });
        assertEquals("p", e.getOption());
    }

    // sad path
    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_not_accept_insufficient_arguments_for_single_value_option(String arguments) {
        InsufficientException e = assertThrows(InsufficientException.class, () -> {
            new SingleValueOptionParser<Integer>(Integer::valueOf, 0).parse(Arrays.asList(arguments.split(" ")), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    // default value
    @Test
    public void should_set_default_value_to_0_for_int_option() {
        assertEquals(0, new SingleValueOptionParser<>(Integer::valueOf, 0).parse(Arrays.asList(), option("p")));
    }


    // happy path
    @Test
    public void should_parse_value_if_flag_present() {
        assertEquals(8080, new SingleValueOptionParser<>(Integer::valueOf, 0).parse(Arrays.asList("-p", "8080"), option("p")));
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
