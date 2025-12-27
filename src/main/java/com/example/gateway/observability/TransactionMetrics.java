package com.example.gateway.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TransactionMetrics {

    private final Counter transactionsCreated;

    public TransactionMetrics(MeterRegistry meterRegistry) {
        this.transactionsCreated = Counter.builder("transactions.created")
                .description("Total number of created transactions")
                .register(meterRegistry);
    }

    public void increment() {
        transactionsCreated.increment();
    }
}
