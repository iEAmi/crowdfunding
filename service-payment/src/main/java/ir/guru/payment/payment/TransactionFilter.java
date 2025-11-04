package ir.guru.payment.payment;

import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

public record TransactionFilter(
        @Nullable String username,
        @Nullable LocalDateTime createdAtFrom,
        @Nullable LocalDateTime createdAtTo) {}
