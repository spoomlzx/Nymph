package com.spoom.nymph.utils;

import java.util.LinkedHashMap;

/**
 * package com.spoom.nymph.utils
 *
 * @author spoomlan
 * @date 2018/10/24
 */
public class BaseResult extends LinkedHashMap<String, Object> {
    private BaseResult() {
    }

    public static BaseResult success() {
        return new BaseResult().put("code", 200);
    }

    public static BaseResult success(String msg) {
        return new BaseResult().put("code", 500).put("msg", msg);
    }

    public static BaseResult error() {
        return new BaseResult().put("code", 500);
    }

    public static BaseResult error(String msg) {
        return new BaseResult().put("code", 500).put("msg", msg);
    }

    public static BaseResult error(Integer code, String msg) {
        return new BaseResult().put("code", code).put("msg", msg);
    }

    public BaseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

