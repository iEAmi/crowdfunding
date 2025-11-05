package ir.guru.payment.payment.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.payment.payment.TransactionAmountRials;
import org.junit.jupiter.api.Test;

class PaymentAmountRialsJpaConverterTest {

    private final PaymentAmountRialsJpaConverter converter = new PaymentAmountRialsJpaConverter();

    @Test
    void convertsAmountToDatabaseColumn() {
        TransactionAmountRials amount = new TransactionAmountRials(3_500L);

        assertThat(converter.convertToDatabaseColumn(amount)).isEqualTo(3_500L);
    }

    @Test
    void convertsDatabaseValueToAmount() {
        TransactionAmountRials amount = converter.convertToEntityAttribute(7_500L);

        assertThat(amount.value()).isEqualTo(7_500L);
    }
}
