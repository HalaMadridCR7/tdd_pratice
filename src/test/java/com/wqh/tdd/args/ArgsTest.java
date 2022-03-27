package com.wqh.tdd.args;

import  static org.junit.jupiter.api.Assertions.*;

import com.wqh.args.Args;
import com.wqh.args.exceptions.IllegalOptionException;
import com.wqh.args.Option;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-19 07:31
 */
public class ArgsTest {

    @Test
    public void should_parse_multi_options() {

        MultiOptions option = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/local");

        assertTrue(option.logging());
        assertEquals(option.port(), 8080);
        assertEquals(option.directory(), "/usr/local");

    }

    public record MultiOptions(@Option("l") Boolean logging, @Option("p") Integer port, @Option("d") String directory) {

    }


    @Test
    public void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class,
                () -> Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/local"));
        assertEquals(e.getParameter(), "port");

    }
    public record OptionsWithoutAnnotation(@Option("l") Boolean logging, Integer port, @Option("d") String directory) {

    }


    //TODO: -g this is a list -d 1 2 -3 5
    @Test
    public void should_example_2() {
        ListOptions option = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(option.group(), new String[] {"this", "is", "a", "list"});
        assertArrayEquals(option.decimal, new Integer[] {1,2,-3, 5});
    }

    public record ListOptions(@Option("g") String[] group, @Option("d") Integer[] decimal) {

    }
}
