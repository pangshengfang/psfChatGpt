package com.psf.core.client;

import com.psf.common.request.MessageRequest;
import com.psf.common.response.ChatGptCompletionResponse;
import com.psf.common.response.OpenAICommonResponse;
import com.psf.common.response.ModelResponse;

import java.util.List;

public interface ChatGptClient {
    OpenAICommonResponse<ModelResponse> listModels();

    ModelResponse retrieveModels(String model);

    ChatGptCompletionResponse createChatCompletion(String model, List<MessageRequest> messages);
}
