package com.wqh.tdd.args;

import  static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wqh.tdd.args.exceptions.IllegalOptionException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

/**
 * @author wqh
 * @date 2022-03-19 07:31
 */
public class ArgsTest {

    @Test
    public void should_parse_multi_options() {
        // sut （system under test ：被测系统） => Args.parse

        // exercise
        MultiOptions option = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/local");
        // verify
        assertTrue(option.logging());
        assertEquals(option.port(), 8080);
        assertEquals(option.directory(), "/usr/local");
        // teardown

    }

    // setup
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

    @Test
    public void should_parse_multi_options_if_parser_provided() {
        OptionParser boolParser = mock(OptionParser.class);
        OptionParser intParser = mock(OptionParser.class);
        OptionParser stringParser = mock(OptionParser.class);

        when(boolParser.parse(Mockito.any(), Mockito.any())).thenReturn(true);
        when(intParser.parse(Mockito.any(), Mockito.any())).thenReturn(0);
        when(stringParser.parse(Mockito.any(), Mockito.any())).thenReturn("parsed");

        Args<MultiOptions> args = new Args<>(MultiOptions.class, Map.of(Boolean.class, boolParser, Integer.class, intParser, String.class, stringParser));
        MultiOptions option = args.parse("-l", "-p", "8080", "-d", "/usr/local");

        assertTrue(option.logging());
        assertEquals(option.port(), 0);
        assertEquals(option.directory(), "parsed");

    }
}
