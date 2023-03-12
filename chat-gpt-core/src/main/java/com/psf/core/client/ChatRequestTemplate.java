package com.psf.core.client;

import com.psf.common.constant.dto.PeopleNameDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRequestTemplate {

    private static final Integer NAME_COUNT = 8;

    public String generatePeopleNameRequest(PeopleNameDTO dto) {
        return String.format("给我推荐%d个%s的名字，标出拼音，姓氏为%s，名字来自于《诗经》或《山海经》或《楚辞》或《周易》等古典文化著作，含有%s等美好寓意，且不与古人和现代名人重名，写出详细的寓意和来历。 输出格式：名字 ｜ 拼音｜ 寓意 ｜ 来历 ｜"
        , NAME_COUNT, dto.getSex(), dto.getFistName(), list2String(dto.getAttributes()));
    }

    private String list2String(List<String> list) {
        return String.join("、", list);
    }

    public Integer getNameCount() {
        return NAME_COUNT;
    }
}
