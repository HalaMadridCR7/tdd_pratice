package com.wqh.tdd.args;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
public interface OptionParser<T> {
    T parse(List<String> arguments, Option option);
}
