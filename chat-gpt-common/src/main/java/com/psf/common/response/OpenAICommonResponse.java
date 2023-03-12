package com.psf.common.response;

import lombok.Data;

import java.util.List;

@Data
public class OpenAICommonResponse<T> {

    private String object;

    private List<T> data;
}
