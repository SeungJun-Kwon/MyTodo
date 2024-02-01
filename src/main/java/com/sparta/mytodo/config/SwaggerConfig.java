package com.sparta.mytodo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        // pathsToMatch로 원하는 경로의 api만 나오도록 설정
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        String title = "My Todo App";
        String description = "Spring 숙련 주차 개인 과제";

        Info info = new Info().title(title).description(description).version("1.0.0");

        return new OpenAPI().info(info);
    }
}