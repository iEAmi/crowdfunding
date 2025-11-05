package ir.guru.payment.payment.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.payment.payment.TransactionUniqueIdentifier;
import org.junit.jupiter.api.Test;

class StringToTransactionUniqueIdentifierConverterTest {

    private final StringToTransactionUniqueIdentifierConverter converter =
            new StringToTransactionUniqueIdentifierConverter();

    @Test
    void convertsStringToIdentifier() {
        TransactionUniqueIdentifier identifier = converter.convert("  identifier-1  ");

        assertThat(identifier).isNotNull();
        assertThat(identifier.toString()).isEqualTo("identifier-1");
    }
}
