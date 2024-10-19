package com.ncs.nucleusproject1.app;

/*@author: Shannon Heng, 10 October 2023*/

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NUSProjectA_MainClass {

        public static void main(String[] args) {
            SpringApplication.run(NUSProjectA_MainClass.class, args);
        }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Demo Ecommerce Project")
                        .description("Testing For Team 1")
                        .version("1.0.0"));
    }
}
