package com.breader.springmicroservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact CONTACT_TO_ME = new Contact(
            "Adrian Chlebosz",
            "github.com/breader124",
            "breaderman@gmail.com"
    );

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Api Documentation",
            "Api Documentation",
            "1.0",
            "urn:tos",
            CONTACT_TO_ME,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>()
    );

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = Set.of("application/json");

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
