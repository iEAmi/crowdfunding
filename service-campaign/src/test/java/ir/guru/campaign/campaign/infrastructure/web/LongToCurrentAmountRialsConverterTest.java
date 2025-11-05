package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.CurrentAmountRials;
import org.junit.jupiter.api.Test;

class LongToCurrentAmountRialsConverterTest {

    private final LongToCurrentAmountRialsConverter converter = new LongToCurrentAmountRialsConverter();

    @Test
    void convertsLongToCurrentAmountRials() {
        CurrentAmountRials amount = converter.convert(1_200L);

        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualTo(1_200L);
    }
}
