package com.ureshii.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "jwt",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(info = @Info(title = "Ureshii API Documentation", version = "2.0", description =
        "Ureshii API Documentation descrioption"),
        servers = {@Server(url = "/ureshii")
        })
public class UreshiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UreshiiApplication.class, args);
    }

}
