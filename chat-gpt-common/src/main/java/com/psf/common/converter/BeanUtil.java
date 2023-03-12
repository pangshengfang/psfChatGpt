package com.psf.common.converter;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.psf.common.response.OpenAICommonResponse;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil {

    public static <T> OpenAICommonResponse<T> parseChatGptCommonRes(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, new TypeReference<OpenAICommonResponse<T>>(clazz) {
        });
    }
}
