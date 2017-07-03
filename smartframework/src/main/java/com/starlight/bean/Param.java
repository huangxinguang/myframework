package com.starlight.bean;

import com.starlight.utils.CastUtil;

import java.util.Map;

/**
 * Created by huangxinguang on 2017/6/30 下午4:38.
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String,Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获long参数值
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     * @return
     */
    public Map<String,Object> getMap() {
        return paramMap;
    }
}
