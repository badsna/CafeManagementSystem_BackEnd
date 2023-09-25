package com.example.cafemanagementsystem.config;

import com.example.cafemanagementsystem.utils.ReadJsonFileToJsonObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.json.JSONException;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import java.io.IOException;

@OpenAPIDefinition
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)


public class SpringFoxConfig {
    @Bean
   public OpenAPI baseOpenAPI() throws JSONException, IOException {

        ReadJsonFileToJsonObject readJsonFileToJsonObject=new ReadJsonFileToJsonObject();

        ApiResponse badRequestAPI=new ApiResponse().content(
                        new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                                        new Example().value(readJsonFileToJsonObject.read().get("badRequestResponse").toString())))
                ).description("Bad Request !!!");

        ApiResponse internalServerErrorAPI=new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("internalServerErrorResponse").toString())))
        ).description("Internal Server Error !!!");

        ApiResponse successAPI=new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("successResponse").toString())))
        ).description("OK !!!");

        ApiResponse forbiddenAPI=new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("forbiddenResponse").toString())))
        ).description("Forbidden !!!");

        ApiResponse unAuthorizedAPI=new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("unAuthorizedResponse").toString())))
        ).description("UnAuthorized !!!");

        Components components=new Components();
        components.addResponses("badRequestAPI",badRequestAPI);
        components.addResponses("internalServerErrorAPI",internalServerErrorAPI);
        components.addResponses("successAPI",successAPI);
        components.addResponses("forbiddenAPI",forbiddenAPI);
        components.addResponses("unAuthorizedAPI", unAuthorizedAPI);

        return new OpenAPI()
                .components(components)

                .info(new Info().title("Spring Doc").version("1.0.0").description("Spring DOc"))
                ;
    }

    @Bean
     public GroupedOpenApi postApi(){
         String []paths={"/user/**"};
         return GroupedOpenApi.builder().group("userPostApi").pathsToMatch(paths).build();
    }
}
