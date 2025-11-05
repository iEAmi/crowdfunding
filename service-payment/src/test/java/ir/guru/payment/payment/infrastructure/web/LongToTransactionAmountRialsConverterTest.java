package ir.guru.payment.payment.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.payment.payment.TransactionAmountRials;
import org.junit.jupiter.api.Test;

class LongToTransactionAmountRialsConverterTest {

    private final LongToTransactionAmountRialsConverter converter = new LongToTransactionAmountRialsConverter();

    @Test
    void convertsLongToAmount() {
        TransactionAmountRials amount = converter.convert(99_999L);

        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualTo(99_999L);
    }
}
