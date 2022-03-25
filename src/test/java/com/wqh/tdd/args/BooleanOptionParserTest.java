package com.wqh.tdd.args;

import static org.junit.jupiter.api.Assertions.*;

import com.wqh.args.BooleanOptionParser;
import com.wqh.args.Option;
import com.wqh.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author wqh
 * @date 2022-03-21 07:12
 */
public class BooleanOptionParserTest {

    // sad path
    @Test
    public void shop_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new BooleanOptionParser().parse(Arrays.asList("-l", "t"), option("l"));
        });
        assertEquals("l", e.getOption());
    }

    // default value
    @Test
    public void shop_set_default_value_to_false_if_option_not_present() {
        Boolean result = new BooleanOptionParser().parse(Arrays.asList(), option("l"));
        assertFalse(result);

    }

    // happ path
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        Boolean result = new BooleanOptionParser().parse(Arrays.asList("-l"), option("l"));
        assertTrue(result);
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
