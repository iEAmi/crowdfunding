package ir.guru.payment.payment.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.payment.payment.TransactionUniqueIdentifier;
import org.junit.jupiter.api.Test;

class TransactionUniqueIdentifierJpaConverterTest {

    private final TransactionUniqueIdentifierJpaConverter converter = new TransactionUniqueIdentifierJpaConverter();

    @Test
    void convertsIdentifierToDatabaseColumn() {
        TransactionUniqueIdentifier identifier = new TransactionUniqueIdentifier("txn-123");

        assertThat(converter.convertToDatabaseColumn(identifier)).isEqualTo("txn-123");
    }

    @Test
    void convertsDatabaseValueToIdentifier() {
        TransactionUniqueIdentifier identifier = converter.convertToEntityAttribute("txn-456");

        assertThat(identifier.toString()).isEqualTo("txn-456");
    }
}
