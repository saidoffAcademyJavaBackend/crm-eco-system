package uz.saidoff.crmecosystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        static {
                var schema = new Schema<LocalTime>();
                schema.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                schema.pattern("HH:mm");
                SpringDocUtils
                        .getConfig()
                        .replaceWithSchema(LocalTime.class, schema);
        }
}
