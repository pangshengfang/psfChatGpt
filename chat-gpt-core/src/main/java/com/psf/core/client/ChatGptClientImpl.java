package com.psf.core.client;

import com.alibaba.fastjson2.JSONObject;
import com.psf.common.converter.BeanUtil;
import com.psf.common.request.MessageRequest;
import com.psf.common.response.ChatGptCompletionResponse;
import com.psf.common.response.OpenAICommonResponse;
import com.psf.common.response.ModelResponse;
import com.psf.core.service.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptClientImpl implements ChatGptClient {

    private final HttpService httpService;

    private final static String HOST = "https://api.openai.com/v1/";

    private final static String TOKEN = "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";

    @Override
    public OpenAICommonResponse<ModelResponse> listModels() {
        String url = HOST + "models";
        String res = httpService.get(url, String.class, generateGetHeaders(), new HashMap<>());
        return BeanUtil.parseChatGptCommonRes(res, ModelResponse.class);
    }

    @Override
    public ModelResponse retrieveModels(String model) {
        String url = HOST + "models/" + model;
        return httpService.get(url, ModelResponse.class, generateGetHeaders(), new HashMap<>());
    }

    @Override
    public ChatGptCompletionResponse createChatCompletion(String model, List<MessageRequest> messages) {
        String url = HOST + "chat/completions";
        JSONObject param = new JSONObject();
        param.put("model", model);
        param.put("messages", messages);
        return httpService.post(url, param.toJSONString(), generatePostHeaders(), ChatGptCompletionResponse.class);
    }

    private Map<String, String> generateGetHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + TOKEN);
        return header;
    }

    private Map<String, String> generatePostHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + TOKEN);
        header.put("Content-Type" ,"application/json");
        return header;
    }
}
