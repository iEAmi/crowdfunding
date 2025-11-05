package ir.guru.payment.payment.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.payment.payment.TransactionAmountRials;
import org.junit.jupiter.api.Test;

class StringToTransactionAmountRialsConverterTest {

    private final StringToTransactionAmountRialsConverter converter = new StringToTransactionAmountRialsConverter();

    @Test
    void convertsStringToAmount() {
        TransactionAmountRials amount = converter.convert("12345");

        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualTo(12_345L);
    }
}
