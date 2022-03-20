package com.wqh.args;

import java.util.List;

/**
 * @author wqh
 * @date 2022-03-20 23:35
 */
class StringOptionParser extends IntOptionParser {

    @Override
    protected Object parseValue(String value) {
        return value;
    }
}
