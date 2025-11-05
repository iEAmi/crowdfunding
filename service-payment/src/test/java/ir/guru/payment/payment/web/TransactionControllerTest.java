package ir.guru.payment.payment.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.payment.payment.TransactionAmountRials;
import ir.guru.payment.payment.TransactionUniqueIdentifier;
import ir.guru.payment.payment.TransactionXerox;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TransactionControllerTest {

    @Test
    void transactionResponseOfCopiesFieldsFromXerox() {
        TransactionXerox xerox = new TransactionXerox(
                5L,
                new TransactionAmountRials(9_000L),
                "bob",
                new TransactionUniqueIdentifier("uid-77"),
                "Donation",
                LocalDateTime.parse("2024-04-01T08:30:00"));

        TransactionController.TransactionResponse response = TransactionController.TransactionResponse.of(xerox);

        assertThat(response.id()).isEqualTo(5L);
        assertThat(response.amountRials()).isEqualTo(xerox.amountRials());
        assertThat(response.username()).isEqualTo("bob");
        assertThat(response.uniqueIdentifier()).isEqualTo(xerox.uniqueIdentifier());
        assertThat(response.description()).isEqualTo("Donation");
        assertThat(response.createdAt()).isEqualTo(LocalDateTime.parse("2024-04-01T08:30:00"));
    }
}
