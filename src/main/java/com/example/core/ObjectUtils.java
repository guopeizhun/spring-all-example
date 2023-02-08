package com.example.core;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/7 16:19
 * @Description:
 */


public class ObjectUtils {
    public static boolean isEmpty(Object o) {
        return null == o;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }
}
