package com.wqh.tdd.args;

import com.wqh.args.exceptions.InsufficientException;
import com.wqh.args.SingleValueOptionParser;
import com.wqh.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.function.Function;

import static com.wqh.tdd.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wqh
 * @date 2022-03-23 07:09
 */
public class SingleValueOptionParserTest {

    // sad path
    @Test
    public void should_not_accept_extra_arguments_for_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            SingleValueOptionParser.unary(Integer::valueOf, 0).parse(Arrays.asList("-p", "8080", "8081"), option("p"));
        });
        assertEquals("p", e.getOption());
    }

    // sad path
    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_not_accept_insufficient_arguments_for_single_value_option(String arguments) {
        InsufficientException e = assertThrows(InsufficientException.class, () -> {
            SingleValueOptionParser.unary(Integer::valueOf, 0).parse(Arrays.asList(arguments.split(" ")), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    // default value
    @Test
    public void should_set_default_value_to_for_single_value_option() {
        Function<String, Object> whatever = it -> null;
        Object defaultValue =  new Object();
        assertSame(defaultValue, SingleValueOptionParser.unary(whatever, defaultValue).parse(Arrays.asList(), option("p")));
    }


    // happy path
    @Test
    public void should_parse_value_if_flag_present() {
        Object parsed =  new Object();
        Function<String, Object> parse = it -> parsed;
        Object defaultValue = new Object();
        assertSame(parsed, SingleValueOptionParser.unary(parse, defaultValue).parse(Arrays.asList("-p", "8080"), option("p")));
    }

}
