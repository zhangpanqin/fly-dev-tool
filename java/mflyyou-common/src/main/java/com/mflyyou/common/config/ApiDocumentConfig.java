package com.mflyyou.common.config;


import com.mflyyou.common.exception.ErrorResponse;
import com.mflyyou.common.swagger.CommonExample;
import com.mflyyou.common.swagger.ExternalExample;
import com.mflyyou.common.swagger.SwaggerProperties;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mflyyou.common.Constants.COMMON_EXAMPLE_WITH_500;
import static com.mflyyou.common.exception.CommonErrors.INTERNAL_SERVER_ERROR;
import static org.springdoc.core.SpringDocUtils.getConfig;

@ConditionalOnProperty(prefix = "app.springdoc", name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
public class ApiDocumentConfig {
    static {
        getConfig()
                .replaceWithClass(Pageable.class, SimplePage.class);
    }

    private final SwaggerProperties swaggerProperties;

    public static Map<String, Example> buildExamples(List<CommonExample> commonExamples, List<ExternalExample> externalExamples) {
        var examples = new HashMap<String, Example>();
        if (Objects.nonNull(commonExamples)) {
            commonExamples.forEach(commonExample ->
                    examples.put(commonExample.getExampleName(), commonExample.getExample()));
        }

        if (Objects.nonNull(externalExamples)) {
            externalExamples.forEach(externalExample -> {
                if (isExampleExisted(examples, externalExample)) {
                    throw new IllegalArgumentException("Example name: " + externalExample.getExampleName() + " has existed.");
                }
                examples.put(externalExample.getExampleName(), externalExample.getExample());
            });
        }
        return examples;
    }

    private static boolean isExampleExisted(HashMap<String, Example> examples, ExternalExample externalExample) {
        return examples.containsKey(externalExample.getExampleName()) && !externalExample.isForceReplace();
    }

    @Bean
    public OpenAPI openAPI(List<ExternalExample> externalExamples) {
        var components = new Components();
        components.setExamples(buildExamples(buildCommonExamples(), externalExamples));
        return new OpenAPI()
                .info(swaggerProperties.getInfo())
                .servers(swaggerProperties.getServers())
                .components(components);
    }

    private List<CommonExample> buildCommonExamples() {
        return List.of(new CommonExample(COMMON_EXAMPLE_WITH_500, new Example()
                .value(ErrorResponse.fromErrorCode(INTERNAL_SERVER_ERROR, Map.of()))));
    }

    @Data
    static class SimplePage {
        @Parameter(description = "page number,start form 0, default 0", example = "0")
        private int page;
        @Parameter(description = "per page should have how many elements, default 10", example = "10")
        private int size;
    }
}
