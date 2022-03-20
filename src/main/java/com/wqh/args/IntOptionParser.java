package com.wqh.args;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
class IntOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int i = arguments.indexOf("-" + option.value());
        return Integer.valueOf(arguments.get(i + 1));
    }
}
