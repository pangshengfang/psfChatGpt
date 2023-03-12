package com.psf.common.constant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleNameDTO {
    private List<String> attributes;

    private String fistName;

    private String sex;

}
