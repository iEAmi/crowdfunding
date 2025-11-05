package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TransactionUniqueIdentifierTest {

    @Test
    void constructorRejectsBlankValues() {
        assertThatThrownBy(() -> new TransactionUniqueIdentifier("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TransactionUniqueIdentifier("   \t")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void trimsValueAndUsesItForStringRepresentation() {
        TransactionUniqueIdentifier identifier = new TransactionUniqueIdentifier("  unique-id  ");

        assertThat(identifier.toString()).isEqualTo("unique-id");
    }
}
