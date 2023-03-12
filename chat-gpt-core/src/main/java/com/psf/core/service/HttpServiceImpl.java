package com.psf.core.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@Service
public class HttpServiceImpl implements HttpService {

    private RestTemplate restTemplate;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }
        return restTemplate;
    }

    @Override
    public String get(String uri) {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(uri, String.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> T get(String uri, Class<T> clazz) {
        ResponseEntity<T> entity = getRestTemplate().getForEntity(uri, clazz);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public String get(String uri, Map<String, ?> parameters) {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(buildUrl(uri, parameters), String.class, parameters);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    private String buildUrl(String uri, Map<String, ?> parameters) {
        StringBuilder builder = new StringBuilder(uri);
        if (!CollectionUtils.isEmpty(parameters)) {
            if (uri.indexOf("{") > 0 && uri.indexOf("}") > 0) {
                //存在匹配不进行处理
            } else {
                if (uri.indexOf("?") == -1) {
                    builder.append("?");
                } else if (!uri.endsWith("?")) {
                    builder.append("&");
                }
                int i = 0, j = parameters.keySet().size();
                for (String s : parameters.keySet()) {
                    builder.append(s).append("={").append(s).append("}");
                    i++;
                    if (j != i) {
                        builder.append("&");
                    }
                }
            }
        }
        return builder.toString();
    }

    @Override
    public String get(String uri, Map<String, String> hs, Map<String, ?> parameters) {
        HttpHeaders headers = new HttpHeaders();
        hs.forEach(headers::add);
        HttpEntity<?> en = new HttpEntity<>(headers);
        ResponseEntity<String> entity = getRestTemplate().exchange(buildUrl(uri, parameters), HttpMethod.GET, en, String.class, parameters);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> T get(String uri, Class<T> clazz, Map<String, String> hs, Map<String, ?> parameters) {
        HttpHeaders headers = new HttpHeaders();
        hs.forEach(headers::add);
        HttpEntity<?> en = new HttpEntity<>(headers);
        ResponseEntity<T> entity = null;
        if (null != parameters) {
            entity = getRestTemplate().exchange(buildUrl(uri, parameters), HttpMethod.GET, en, clazz, parameters);
        } else {
            entity = getRestTemplate().exchange(buildUrl(uri, parameters), HttpMethod.GET, en, clazz);
        }
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }


    @Override
    public <T> HttpHeaders getheader(String uri, Class<T> clazz, Map<String, String> hs, Map<String, ?> parameters) {
        HttpHeaders headers = new HttpHeaders();
        if (hs == null || hs.size() == 0) {
            hs.put("header", "header");
        }
        hs.forEach(headers::add);
        HttpEntity<?> en = new HttpEntity<>(headers);
        ResponseEntity<T> entity;
        if (null != parameters) {
            entity = getRestTemplate().exchange(buildUrl(uri, parameters), HttpMethod.GET, en, clazz, parameters);
        } else {
            entity = getRestTemplate().exchange(buildUrl(uri, parameters), HttpMethod.GET, en, clazz);
        }
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getHeaders();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> T get(String uri, Map<String, String> headers, Map<String, Object> formData, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> en = new HttpEntity<>(formData.toString(), httpHeaders);
        ResponseEntity<T> entity = getRestTemplate().exchange(URI.create(uri), HttpMethod.GET, en, clazz);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public String post(String uri) {
        ResponseEntity<String> entity = getRestTemplate().postForEntity(uri, null, String.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public String post(String uri, Object parameters) {
        ResponseEntity<String> entity = getRestTemplate().postForEntity(uri, parameters, String.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> T post(String uri, Object parameters, Class<T> resultType) {
        log.info("post for url: " + uri);
        ResponseEntity<T> entity = getRestTemplate().postForEntity(uri, parameters, resultType);
        log.warn("entity: " + entity);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> T post(String uri, Object parameters,
                      @NonNull final Map<String, String> headers, Class<T> resultType) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            for (Map.Entry<String, String> keyValue : headers.entrySet()) {
                request.getHeaders().set(keyValue.getKey(), keyValue.getValue());
            }
            return execution.execute(request, body);
        });
        ResponseEntity<T> entity = restTemplate.postForEntity(uri, parameters, resultType);
//        log.warn("entity: " + entity);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> HttpHeaders postheader(String uri, Object parameters,
                                      Map<String, String> headers, Class<T> resultType) {
        if (headers == null || headers.size() == 0) {
            headers.put("header", "header");
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            for (Map.Entry<String, String> keyValue : headers.entrySet()) {
                request.getHeaders().set(keyValue.getKey(), keyValue.getValue());
            }
            return execution.execute(request, body);
        });
        ResponseEntity<T> entity = restTemplate.postForEntity(uri, parameters, resultType);
        log.warn("entity: " + entity);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getHeaders();
        }
        throw new ServiceException(getErrorMsg(entity));
    }

    @Override
    public <T> T formPost(String uri, MultiValueMap<String, Object> parameters, Class<T> resultType, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        header.forEach(headers::add);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<T> response = getRestTemplate().postForEntity(uri, request, resultType);
        log.info("formPost result:{} ", response);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ServiceException(getErrorMsg(response));
    }


    private String getErrorMsg(ResponseEntity entity) {
        return "request error, code=" + entity.getStatusCode()
                + ", body=" + (entity.getBody() == null ? "null" : entity.getBody().toString());
    }
}
