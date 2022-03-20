package com.wqh.args;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
class StringOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int i = arguments.indexOf("-" + option.value());
        return arguments.get(i + 1);
    }
}
