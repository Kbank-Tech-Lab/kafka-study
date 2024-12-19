package org.coreBanking.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import lombok.Setter;

@Setter
public class DynamicClock extends Clock {
    private volatile Clock clock;

    public DynamicClock(Clock initialClock) {
        this.clock = initialClock;
    }

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return clock.withZone(zone);
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }
}
