package ir.guru.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableWebSecurity
@SpringBootApplication
public class ServiceUserApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}

