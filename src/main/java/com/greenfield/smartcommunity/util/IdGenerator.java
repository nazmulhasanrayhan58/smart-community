package com.greenfield.smartcommunity.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {

    private static final IdGenerator INSTANCE = new IdGenerator();
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    private IdGenerator() {}

    public static IdGenerator getInstance() { return INSTANCE; }

    public String nextRequestId() {
        return String.format("REQ-%04d", requestCounter.incrementAndGet());
    }
}