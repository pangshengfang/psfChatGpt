package com.psf.core.persistence.converter;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class List2StringConverter implements AttributeConverter<List<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        if (!CollectionUtils.isEmpty(list)) {
            Collections.sort(list);
            return list.stream().map(Object::toString).collect(Collectors.joining(","));
        }
        return "";
    }

    @Override
    public List<Integer> convertToEntityAttribute(String s) {
        if (StringUtils.isEmpty(s)) {
            return Collections.emptyList();
        }
        return Arrays.stream(s.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }
}
