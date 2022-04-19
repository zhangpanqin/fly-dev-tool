package com.mflyyou.demo.controller;

import com.mflyyou.demo.api.OrgApi;
import com.mflyyou.demo.api.request.CreateOrgRequest;
import com.mflyyou.demo.api.response.OrgResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/demo")
@Validated
public class DemoController implements OrgApi {

    /**
     * 必须在类上加上  @Validated
     */

    @Override
    @GetMapping
    public OrgResponse getById(@NotNull(message = "id 不能为空") Long id) {
        return OrgResponse.builder()
                .id(2L)
                .build();
    }

    @Override
    @PostMapping
    public OrgResponse create2ByParam(@Valid CreateOrgRequest request) {
        return OrgResponse.builder()
                .id(2L)
                .name(request.getName())
                .build();
    }

    @Override
    @PutMapping
    public Long createByJson(@Valid @RequestBody CreateOrgRequest request) {
        return 2L;
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {

    }
}
