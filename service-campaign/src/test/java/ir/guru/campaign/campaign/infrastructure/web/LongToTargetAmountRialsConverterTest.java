package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.TargetAmountRials;
import org.junit.jupiter.api.Test;

class LongToTargetAmountRialsConverterTest {

    private final LongToTargetAmountRialsConverter converter = new LongToTargetAmountRialsConverter();

    @Test
    void convertsLongToTargetAmountRials() {
        TargetAmountRials targetAmountRials = converter.convert(5_000L);

        assertThat(targetAmountRials).isNotNull();
        assertThat(targetAmountRials.value()).isEqualTo(5_000L);
    }
}
