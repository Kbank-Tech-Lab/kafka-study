package org.coreBanking.config;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {

    @Bean
    public Clock clock() {
        // 현재 시간
        Clock systemClock = Clock.systemDefaultZone();

        // 오늘 날짜의 23시 45분
        LocalDate today = LocalDate.now(systemClock.getZone());
        LocalTime targetTime = LocalTime.of(23, 45, 0);
        Instant targetInstant = today.atTime(targetTime).atZone(systemClock.getZone()).toInstant();

        // 현재 시간과 목표 시간의 차이 계산
        Instant now = Instant.now(systemClock);
        Duration offsetDuration = Duration.between(now, targetInstant);

        // Offset 적용된 Clock 생성
        return Clock.offset(systemClock, offsetDuration);
    }
}

