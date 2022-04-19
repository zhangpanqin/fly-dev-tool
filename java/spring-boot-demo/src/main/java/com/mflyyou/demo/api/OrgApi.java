package com.mflyyou.demo.api;

import com.mflyyou.demo.api.request.CreateOrgRequest;
import com.mflyyou.demo.api.response.OrgResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface OrgApi {

    OrgResponse getById(@NotNull(message = "id 不能为空") Long id);

    OrgResponse create2ByParam(@Valid CreateOrgRequest request);

    Long createByJson(@Valid CreateOrgRequest request);


    void deleteById(Long id);
}
