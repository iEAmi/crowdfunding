package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.DonationAmountRials;
import org.junit.jupiter.api.Test;

class LongToDonationAmountRialsConverterTest {

    private final LongToDonationAmountRialsConverter converter = new LongToDonationAmountRialsConverter();

    @Test
    void convertsLongToDonationAmountRials() {
        DonationAmountRials amount = converter.convert(99_999L);

        assertThat(amount).isNotNull();
        assertThat(amount.value()).isEqualTo(99_999L);
    }
}
