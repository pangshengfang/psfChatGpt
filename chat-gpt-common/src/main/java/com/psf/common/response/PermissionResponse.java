package com.psf.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PermissionResponse {

    private String id;

    private String object;

    private Long created;

    @JsonProperty("allow_create_engine")
    private Boolean allowCreateEngine;

    @JsonProperty("allow_sampling")
    private Boolean allowSampling;

    @JsonProperty("allow_logprobs")
    private Boolean allowLogprobs;

    @JsonProperty("allow_search_indices")
    private Boolean allowSearchIndices;

    @JsonProperty("allow_view")
    private Boolean allowView;

    @JsonProperty("allow_fine_tuning")
    private Boolean allowFineTuning;

    @JsonProperty("is_blocking")
    private Boolean isBlocking;

    private String organization;

    private String group;
}
