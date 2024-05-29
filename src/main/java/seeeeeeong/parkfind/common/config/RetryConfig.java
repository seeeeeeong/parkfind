package seeeeeeong.parkfind.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.support.RetryTemplate;

@EnableJpaAuditing
@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        return new RetryTemplate();
    }
}
