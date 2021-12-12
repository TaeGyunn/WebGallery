package WebGallery.Gallery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {

        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("WGallery API")
                .description("WGallery API Info")
                .contact(new Contact("TaeGyun", "https://github.com/TaeGyunn/WebGallery.git","cjstkzlxl98@gmail.com"))
                .build();
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }



    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

}
