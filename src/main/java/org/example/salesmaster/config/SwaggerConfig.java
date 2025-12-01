package org.example.salesmaster.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig
 * -----------------------------------------------------
 * ✔ Habilita documentación interactiva de la API con Swagger UI (OpenAPI 3)
 * ✔ Añade soporte para autenticación JWT tipo Bearer
 * ✔ Permite probar todos los endpoints directamente desde el navegador
 * ✔ URL de acceso: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SalesMaster PRO API - Sistema de Gestión de Ventas")
                        .version("1.0.0")
                        .description("API REST segura con JWT para gestión de ventas, clientes, productos, pedidos y facturación."))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT token obtenido del endpoint /api/auth/login")))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("salesmaster-api")
                .pathsToMatch("/api/**")
                .build();
    }
}

