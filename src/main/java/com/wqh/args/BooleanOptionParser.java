package com.wqh.args;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
public class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if(index + 1 < arguments.size()
                &&  !arguments.get(index + 1).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        return index!=-1;
    }
}
