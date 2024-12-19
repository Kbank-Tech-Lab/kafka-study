package org.coreBanking.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {

    @Bean
    public DynamicClock clock() {
        return new DynamicClock(Clock.system(ZoneId.systemDefault()));
    }
}
