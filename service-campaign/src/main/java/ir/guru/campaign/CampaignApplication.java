package ir.guru.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableWebSecurity
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "ir.guru.campaign")
class CampaignApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampaignApplication.class, args);
    }
}
