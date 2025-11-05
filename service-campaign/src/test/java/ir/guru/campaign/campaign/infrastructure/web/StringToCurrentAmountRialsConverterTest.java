package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.CurrentAmountRials;
import org.junit.jupiter.api.Test;

class StringToCurrentAmountRialsConverterTest {

    private final StringToCurrentAmountRialsConverter converter = new StringToCurrentAmountRialsConverter();

    @Test
    void convertsStringToCurrentAmountRials() {
        CurrentAmountRials amount = converter.convert("789");

        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualTo(789L);
    }
}
