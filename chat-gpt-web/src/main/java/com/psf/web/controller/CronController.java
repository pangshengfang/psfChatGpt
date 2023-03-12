package com.psf.web.controller;

import com.psf.core.aspect.CronLog;
import com.psf.core.service.AINameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chat-gpt/cron")
public class CronController {

    private final AINameService aiNameService;

    @CronLog
    @GetMapping("/model")
    public void modelJob() {
        aiNameService.updateModelList();
    }

}
