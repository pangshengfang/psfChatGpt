package com.psf.web.controller;

import com.psf.common.constant.dto.PeopleNameDTO;
import com.psf.common.response.PeopleNameResponse;
import com.psf.core.service.AINameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chat-gpt/ai-name")
public class AINameController {

    private final AINameService aiNameService;

    @PostMapping("/people-name")
    public List<PeopleNameResponse> getPeopleNames(@RequestBody PeopleNameDTO dto) {
        return aiNameService.getPeopleNames(dto);
    }

}
