package dev.hexa.pmsservice.infrastructure.config;

import dev.hexa.pmsservice.application.annotation.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "dev.hexa.pmsservice.application",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = DomainService.class)
        }
)
public class DomainConfig {
}
