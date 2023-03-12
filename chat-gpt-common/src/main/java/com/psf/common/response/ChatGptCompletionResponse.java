package com.psf.common.response;

import lombok.Data;

import java.util.List;

@Data
public class ChatGptCompletionResponse {

    private String id;

    private String object;

    private Long created;

    private String model;

    private UsageResponse usage;

    private List<ChoiceResponse> choices;

}
