package com.psf.core.service;

import lombok.NonNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface HttpService {
    String get(String uri);

    <T> T get(String uri, Class<T> clazz);

    String get(String uri, Map<String, ?> parameters);

    String get(String uri, Map<String, String> headers, Map<String, ?> parameters);

    <T> T get(String uri, Class<T> clazz, Map<String, String> headers, Map<String, ?> parameters);

    <T> HttpHeaders getheader(String uri, Class<T> clazz, Map<String, String> hs, Map<String, ?> parameters);

    <T> T get(String uri, Map<String, String> headers, Map<String, Object> formData, Class<T> clazz);

    String post(String uri);

    String post(String uri, Object parameters);

    <T> T post(String uri, Object parameters, Class<T> resultType);

    <T> T post(String uri, Object parameters,
               @NonNull Map<String, String> headers, Class<T> resultType);

    <T> HttpHeaders postheader(String uri, Object parameters,
                                                        Map<String, String> headers, Class<T> resultType);

    <T> T formPost(String uri, MultiValueMap<String, Object> parameters, Class<T> resultType, Map<String, String> header);
}
