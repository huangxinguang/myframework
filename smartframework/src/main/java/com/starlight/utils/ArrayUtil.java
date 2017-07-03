package com.starlight.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by huangxinguang on 2017/6/30 下午3:45.
 */
public class ArrayUtil {
    /**
     * 数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    /**
     * 是否不为空
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }
}
