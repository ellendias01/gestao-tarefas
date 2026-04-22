package com.gestaotarefas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gerenciamento de Tarefas")
                .version("1.0.0")
                .description("API RESTful para gerenciamento de tarefas do dia a dia.\n\n" +
                            "### Funcionalidades:\n" +
                            "- ✅ Criar tarefa\n" +
                            "- ✅ Listar todas as tarefas\n" +
                            "- ✅ Buscar tarefa por ID\n" +
                            "- ✅ Atualizar tarefa\n" +
                            "- ✅ Deletar tarefa\n\n" +
                            "### Tecnologias:\n" +
                            "- Spring Boot 3.1.5\n" +
                            "- PostgreSQL 18\n" +
                            "- JPA/Hibernate")
                .contact(new Contact()
                    .name("Gestão de Tarefas")
                    .email("contato@gestaotarefas.com"))
                .license(new License()
                    .name("MIT")
                    .url("https://opensource.org/licenses/MIT")));
    }
}