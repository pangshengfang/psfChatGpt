package com.psf.core.service;

import com.psf.common.constant.dto.PeopleNameDTO;
import com.psf.common.constant.enums.ErrorCodeMsg;
import com.psf.common.request.MessageRequest;
import com.psf.common.response.*;
import com.psf.core.client.ChatGptClient;
import com.psf.core.client.ChatRequestTemplate;
import com.psf.core.persistence.dao.ModelDao;
import com.psf.core.persistence.dao.PermissionDao;
import com.psf.core.persistence.entity.ModelEntity;
import com.psf.core.persistence.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AINameServiceImpl implements AINameService {

    private final ChatGptClient client;

    private final PermissionDao permissionDao;

    private final ChatRequestTemplate template;

    private final ModelDao modelDao;

    private final static String DEFAULT_ROLE = "user";

    private final static String DEFAULT_MODEL = "gpt-3.5-turbo";

    @Transactional
    @Override
    public void updateModelList() {
        OpenAICommonResponse<ModelResponse> commonResponse = client.listModels();
        truncateOldModelData();
        List<ModelResponse> models = commonResponse.getData();
        for (ModelResponse modelResponse : models) {
            List<PermissionResponse> permissions = modelResponse.getPermission();
            List<Integer> permissionIds = permissions.stream().map(s -> {
                PermissionEntity en = permissionDao.save(permissionRes2Entity(s));
                return en.getId();
            }).collect(Collectors.toList());
            modelDao.save(modelRes2Entity(modelResponse, permissionIds));
        }
    }

    @Override
    public List<PeopleNameResponse> getPeopleNames(PeopleNameDTO dto) {
        MessageRequest message = new MessageRequest();
        String content = template.generatePeopleNameRequest(dto);
        message.setRole(DEFAULT_ROLE);
        message.setContent(content);
        List<MessageRequest> messageRequests = List.of(message);
        ChatGptCompletionResponse response = client.createChatCompletion(DEFAULT_MODEL, messageRequests);
        return translatePeopleName(response);
    }

    private void truncateOldModelData() {
        permissionDao.deleteAll();
        modelDao.deleteAll();
    }

    private PermissionEntity permissionRes2Entity(PermissionResponse res) {
        PermissionEntity entity = new PermissionEntity();
        entity.setPermissionName(res.getId());
        entity.setType(res.getObject());
        entity.setCreatedAt(LocalDateTime.ofEpochSecond(res.getCreated(), 0, ZoneOffset.UTC));
        entity.setOrganization(res.getOrganization());
        entity.setGroup(res.getGroup());
        entity.setAllowCreateEngine(res.getAllowCreateEngine());
        entity.setAllowSampling(res.getAllowSampling());
        entity.setAllowLogprobs(res.getAllowLogprobs());
        entity.setAllowFineTuning(res.getAllowFineTuning());
        entity.setIsBlocking(res.getIsBlocking());
        return entity;
    }

    private ModelEntity modelRes2Entity(ModelResponse res, List<Integer> permissionIds) {
        ModelEntity entity = new ModelEntity();
        entity.setModelName(res.getId());
        entity.setType(res.getObject());
        entity.setRoot(res.getRoot());
        entity.setParent(res.getParent());
        entity.setOwnedBy(res.getOwnedBy());
        entity.setCreatedAt(LocalDateTime.ofEpochSecond(res.getCreated(), 0, ZoneOffset.UTC));
        entity.setPermissionIds(permissionIds);
        return entity;
    }

    private List<PeopleNameResponse> translatePeopleName(ChatGptCompletionResponse res) {
        String content = res.getChoices().stream().findFirst().orElseThrow(ErrorCodeMsg.BAD_REQUEST::newException).getMessage().getContent();
        List<PeopleNameResponse> result = new ArrayList<>();
        for (int i = 1; i <= template.getNameCount(); i++) {
            PeopleNameResponse peopleNameResponse = new PeopleNameResponse();
            Pattern pattern = Pattern.compile(String.format("%d\\.(.*)\\|(.*)\\|(.*)\\|(.*)\\\n", i)); // 使用正则表达式匹配"b"和"e"之间的任意字符
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                peopleNameResponse.setName(justifyString(matcher.group(1)));
                peopleNameResponse.setPinyin(justifyString(matcher.group(2)));
                peopleNameResponse.setInfo(justifyString(matcher.group(3)));
                peopleNameResponse.setFrom(justifyString(matcher.group(4)));
                result.add(peopleNameResponse);
            }
        }
        return result;
    }

    private String justifyString(String str) {
        if (str.startsWith(" ")) {
            str = str.substring(1);
        }
        if (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}
