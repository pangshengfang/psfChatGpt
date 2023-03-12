package com.psf.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChoiceResponse {

    private MessageResponse message;

    @JsonProperty("finish_reason")
    private String finishReason;

    private Integer index;

}
