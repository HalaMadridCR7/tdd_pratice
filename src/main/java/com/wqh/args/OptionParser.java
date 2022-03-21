package com.wqh.args;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
interface OptionParser<T> {
    Object parse(List<String> arguments, Option option);
}
