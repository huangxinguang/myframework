package com.starlight.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/6/29 上午11:51.
 */
public class CollectionUtil {
    /**
     * 集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 集合不为空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * map为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * map不为空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?,?> map) {
        return !isEmpty(map);
    }
}
