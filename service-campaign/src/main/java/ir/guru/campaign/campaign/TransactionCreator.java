package ir.guru.campaign.campaign;

import lombok.Getter;

public interface TransactionCreator {
    void createTransaction(DonationXerox donation) throws TransactionCreationException;

    sealed abstract class TransactionCreationException extends Exception {
        private TransactionCreationException(String message) {
            super(message);
        }

        public static DuplicateTransactionException duplicateTransactionException(DonationXerox donation) {
            return new DuplicateTransactionException(donation);
        }

        public static UnknownTransactionException unknownTransactionException(DonationXerox donation) {
            return new UnknownTransactionException(donation);
        }

        @Getter
        public static final class DuplicateTransactionException extends TransactionCreationException {
            private final DonationXerox donation;

            private DuplicateTransactionException(DonationXerox donation) {
                super("Duplicate donation transaction");
                this.donation = donation;
            }
        }

        @Getter
        public static final class UnknownTransactionException extends TransactionCreationException {
            private final DonationXerox donation;

            private UnknownTransactionException(DonationXerox donation) {
                super("Unknown transaction creation exception");
                this.donation = donation;
            }
        }
    }
}
