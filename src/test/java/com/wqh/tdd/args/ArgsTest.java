package com.wqh.tdd.args;

import  static org.junit.jupiter.api.Assertions.*;

import com.wqh.args.Args;
import com.wqh.args.Option;
import org.junit.jupiter.api.Test;

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
}
