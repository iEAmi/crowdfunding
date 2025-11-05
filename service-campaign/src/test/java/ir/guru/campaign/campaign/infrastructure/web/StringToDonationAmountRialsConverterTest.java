package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.DonationAmountRials;
import org.junit.jupiter.api.Test;

class StringToDonationAmountRialsConverterTest {

    private final StringToDonationAmountRialsConverter converter = new StringToDonationAmountRialsConverter();

    @Test
    void convertsStringToDonationAmountRials() {
        DonationAmountRials amount = converter.convert("4321");

        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualTo(4_321L);
    }
}
