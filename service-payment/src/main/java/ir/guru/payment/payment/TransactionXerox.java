package ir.guru.payment.payment;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.lang.Nullable;

public record TransactionXerox(
        Long id,
        TransactionAmountRials amountRials,
        String username,
        TransactionUniqueIdentifier uniqueIdentifier,
        @Nullable String description,
        LocalDateTime createdAt) {

    // TODO: write test
    static TransactionXerox of(Transaction transaction) {
        return new TransactionXerox(
                transaction.getId(),
                transaction.getAmountRials(),
                transaction.getUsername(),
                transaction.getUniqueIdentifier(),
                transaction.getDescription(),
                transaction.getCreatedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TransactionXerox that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
