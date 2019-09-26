package com.example.shiro.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;

/**
 * [校验参数属否为空]
 *
 * @author zhangdi
 * @date 2019-05-20 14:29
 */
public class ParamsCheckUtils {

    /**
     * 校验是否为空 true-为空，false-不为空
     *
     * @param objects 入参
     */
    public static boolean isNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object o : objects) {
            if (o == null || "".equals(o)) {
                return true;
            }
            if (o instanceof Collections) {
                return CollectionUtils.isEmpty((Collection<?>) o);
            }
            if (o instanceof Array) {
                return Array.getLength(o) > 0;
            }
        }
        return false;
    }
}
