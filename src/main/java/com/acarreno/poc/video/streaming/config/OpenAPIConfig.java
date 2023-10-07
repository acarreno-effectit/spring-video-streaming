package com.acarreno.poc.video.streaming.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Video Streaming API", version = "1.0",
        description = "Video Streaming Rest API"),
    servers = {@Server(url = "http://localhost:8080", description = "Local URL"),
        @Server(url = "http://server-dev:8080", description = "Server DEV"),
        @Server(url = "http://server-qa:8080", description = "Server QA")})
public class OpenAPIConfig {

  @Bean
  OpenAPI customizeOpenAPI() {
    final String securitySchemeName = "Autorizacion Bearer";
    return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(new Components().addSecuritySchemes(securitySchemeName,
            new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
                .scheme("bearer").bearerFormat("JWT")));
  }
}
