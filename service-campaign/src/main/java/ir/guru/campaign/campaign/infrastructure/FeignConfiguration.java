package ir.guru.campaign.campaign.infrastructure;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class FeignConfiguration {
    @Bean
    Retryer retryer() {
        return new Retryer.Default();
    }
}
