package com.wqh.args;

import com.wqh.args.exceptions.InsufficientException;
import com.wqh.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
public class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return SingleValueOptionParser.values(arguments, option, 0).map(it -> true).orElse(false);
    }
}
