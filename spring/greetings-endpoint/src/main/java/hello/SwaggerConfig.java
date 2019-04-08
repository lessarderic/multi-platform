package hello;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** Configures Swagger 2 for this REST endpoint and enable Springfox validation. */
@Configuration
@EnableSwagger2
@Import(value = {springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
@SuppressWarnings("unused")
public class SwaggerConfig {

  /** Creates the Springfox' {@link Docket} for this API. */
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("hello.endpoint"))
        .paths(regex("/greeting.*"))
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Greetings REST API",
        "Spring Boot REST API for Greetings",
        "1.0",
        "https://somewebsite.com",
        new Contact("Eric Lessard", "https://www.connexta.com/about/", "eric.lessard@connexta.com"),
        "Apache License Version 2.0",
        "https://www.apache.org/licenses/LICENSE-2.0",
        Collections.emptyList());
  }
}
