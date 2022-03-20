package com.wqh.argstdd;

import  static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author wqh
 * @date 2022-03-19 07:31
 */
public class ArgsTest {


    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
//        Options options = Args.parse(Options.class, "-l", "-p", "-d");
        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_false_if_no_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    public record BooleanOption(@Option("l") Boolean logging) {

    }

    @Test
    public void should_parse_int_as_option_value() {
        IntegerOption option = Args.parse(IntegerOption.class, "-p", "8080");
        assertEquals(option.port(), 8080);
    }

    public record IntegerOption(@Option("p") Integer port) {

    }

    @Test
    public void should_get_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/local");
        assertEquals(option.directory(), "/usr/local");
    }

    public record StringOption(@Option("d") String directory) {

    }

    @Test
    public void should_parse_multi_options() {

        MultiOptions option = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/local");

        assertTrue(option.logging());
        assertEquals(option.port(), 8080);
        assertEquals(option.directory(), "/usr/local");

    }

    public record MultiOptions(@Option("l") Boolean logging, @Option("p") Integer port, @Option("d") String directory) {

    }
}
