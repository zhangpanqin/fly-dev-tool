package com.mflyyou.common.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "app.springdoc")
public class SwaggerProperties {
    private boolean enable;

    @NestedConfigurationProperty
    private Info info;

    private List<Server> servers = List.of(buildServer());

    private Server buildServer() {
        return new Server()
                .url("/")
                .description("Default Server URL");
    }
}
