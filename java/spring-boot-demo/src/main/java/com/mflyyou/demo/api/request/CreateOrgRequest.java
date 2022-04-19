package com.mflyyou.demo.api.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateOrgRequest {
    @NotEmpty(message = "name 不能为空")
    private String name;
}
