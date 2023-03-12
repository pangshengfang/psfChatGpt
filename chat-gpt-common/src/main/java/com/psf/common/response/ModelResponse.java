package com.psf.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ModelResponse {

    private String id;

    private String object;

    private Long created;

    @JsonProperty("owned_by")
    private String ownedBy;

    private List<PermissionResponse> permission;

    private String root;

    private String parent;

}
