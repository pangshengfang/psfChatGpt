package com.psf.core.service;

import com.psf.common.response.ModelResponse;
import com.psf.common.response.OpenAICommonResponse;
import com.psf.common.response.PermissionResponse;
import com.psf.core.client.ChatGptClient;
import com.psf.core.persistence.dao.ModelDao;
import com.psf.core.persistence.dao.PermissionDao;
import com.psf.core.persistence.entity.ModelEntity;
import com.psf.core.persistence.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AINameServiceImpl implements AINameService {

    private final ChatGptClient client;

    private final PermissionDao permissionDao;

    private final ModelDao modelDao;

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
}
