package ir.guru.payment.payment;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record TransactionFilter(
        @Nullable String username,
        @Nullable LocalDateTime createdAtFrom,
        @Nullable LocalDateTime createdAtTo) {}
