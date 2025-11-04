package ir.guru.payment.payment;

import org.springframework.lang.Nullable;

public interface TransactionImporter {
    String username();

    @Nullable
    String description();

    TransactionAmountRials amountRials();

    TransactionUniqueIdentifier uniqueIdentifier();
}
