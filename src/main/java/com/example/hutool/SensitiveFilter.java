package com.example.hutool;

import cn.hutool.core.lang.Filter;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/6 17:04
 * @Description:
 */


public class SensitiveFilter implements Filter<Character> {
    @Override
    public boolean accept(Character s) {
        System.out.println(s);
        return false;
    }
}
