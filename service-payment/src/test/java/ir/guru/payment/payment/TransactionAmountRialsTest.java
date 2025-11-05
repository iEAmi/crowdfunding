package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TransactionAmountRialsTest {

    @Test
    void constructorRejectsNonPositiveValues() {
        assertThatThrownBy(() -> new TransactionAmountRials(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TransactionAmountRials(-10)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exposesValueAndStringRepresentation() {
        TransactionAmountRials amount = new TransactionAmountRials(1_000L);

        assertThat(amount.value()).isEqualTo(1_000L);
        assertThat(amount.toString()).isEqualTo("1000");
    }
}
