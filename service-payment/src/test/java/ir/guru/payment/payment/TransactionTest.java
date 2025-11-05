package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TransactionTest {

    @Test
    void newTransactionCreatesEntityWithProvidedData() {
        TransactionAmountRials amount = new TransactionAmountRials(5_000L);
        TransactionUniqueIdentifier uniqueIdentifier = new TransactionUniqueIdentifier("txn-123");
        String username = "john.doe";
        String description = "Support payment";

        Transaction transaction = Transaction.newTransaction(amount, username, uniqueIdentifier, description);

        assertThat(transaction.getId()).isNull();
        assertThat(transaction.getAmountRials()).isEqualTo(amount);
        assertThat(transaction.getUsername()).isEqualTo(username);
        assertThat(transaction.getUniqueIdentifier()).isEqualTo(uniqueIdentifier);
        assertThat(transaction.getDescription()).isEqualTo(description);
    }
}
