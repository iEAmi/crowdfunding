package ir.guru.payment.payment;

import lombok.Getter;

public abstract sealed class TransactionCreationException extends Exception {
    private TransactionCreationException(String message) {
        super(message);
    }

    static DuplicateTransactionException duplicateTransactionException(TransactionUniqueIdentifier uniqueIdentifier) {
        return new DuplicateTransactionException(uniqueIdentifier);
    }

    @Getter
    public static final class DuplicateTransactionException extends TransactionCreationException {
        private final TransactionUniqueIdentifier uniqueIdentifier;

        private DuplicateTransactionException(TransactionUniqueIdentifier uniqueIdentifier) {
            super(String.format("Duplicate transaction identifier: %s", uniqueIdentifier));
            this.uniqueIdentifier = uniqueIdentifier;
        }
    }
}
