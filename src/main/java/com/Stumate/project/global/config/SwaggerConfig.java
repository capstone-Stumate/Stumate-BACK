package com.Stumate.project.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stumate API")        // 여기가 제목
                        .version("v1.0")              // 여기가 버전
                        .description("Stumate 서비스 API 명세서"));  // 여기가 설명
    }
}