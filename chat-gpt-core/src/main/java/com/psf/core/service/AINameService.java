package com.psf.core.service;

import com.psf.common.constant.dto.PeopleNameDTO;
import com.psf.common.response.PeopleNameResponse;

import java.util.List;

public interface AINameService {

    void updateModelList();

    List<PeopleNameResponse> getPeopleNames(PeopleNameDTO dto);
}
