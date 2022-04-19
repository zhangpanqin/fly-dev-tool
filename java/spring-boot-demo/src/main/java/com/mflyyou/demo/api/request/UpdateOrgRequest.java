package com.mflyyou.demo.api.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateOrgRequest {
    @NotNull
    private Long id;
    @NotEmpty(message = "name 不能为空")
    private String name;
}
