package com.mflyyou.demo.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrgResponse {
    private String name;
    private Long id;
}
