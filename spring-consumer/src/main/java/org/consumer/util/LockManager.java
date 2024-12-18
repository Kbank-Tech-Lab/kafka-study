package org.consumer.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockManager {
    private final ConcurrentHashMap<String, ReentrantLock> userLocks = new ConcurrentHashMap<>();

    public ReentrantLock getUserLock(String userId) {
        return userLocks.computeIfAbsent(userId, key -> new ReentrantLock(true));
    }
}
