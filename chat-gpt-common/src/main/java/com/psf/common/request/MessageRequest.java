package com.psf.common.request;

import lombok.Data;

@Data
public class MessageRequest {

    private String role;

    private String content;
}
