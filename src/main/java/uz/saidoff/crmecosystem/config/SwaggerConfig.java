package uz.saidoff.crmecosystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(info = @Info(
  description = "OpenAPI documentation for CRM EcoSystem",
  title = "CRM EcoSystem",
  version = "1.0"
),
  security = @SecurityRequirement(
    name = "BearerAuth"
  )
)
@SecurityScheme(
  name = "BearerAuth",
  description = "JWT-based authentication",
  scheme = "bearer",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenApiCustomizer openApiCustomizer() {
                return openApi -> openApi.servers(List.of(new Server().url("https://back.crm.saidoff.uz")));
        }
}
