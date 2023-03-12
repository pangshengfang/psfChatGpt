package com.psf.web.controller;

import com.psf.common.request.MessageRequest;
import com.psf.common.response.ChatGptCompletionResponse;
import com.psf.common.response.ModelResponse;
import com.psf.common.response.OpenAICommonResponse;
import com.psf.core.client.ChatGptClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ChatGptClient client;


    @GetMapping("/test")
    public OpenAICommonResponse<ModelResponse> test() {
        return client.listModels();
    }

    @GetMapping("/retrieve")
    public ModelResponse gaga() {
        return client.retrieveModels("gpt-3.5-turbo");
    }

    @GetMapping("/chat")
    public ChatGptCompletionResponse chat(@RequestParam String content) {
        MessageRequest message = new MessageRequest();
        message.setRole("user");
        message.setContent(content);
        List<MessageRequest> messageRequests = List.of(message);
        return client.createChatCompletion("gpt-3.5-turbo", messageRequests);
    }
}
