package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.TargetAmountRials;
import org.junit.jupiter.api.Test;

class StringToTargetAmountRialsConverterTest {

    private final StringToTargetAmountRialsConverter converter = new StringToTargetAmountRialsConverter();

    @Test
    void convertsStringToTargetAmountRials() {
        TargetAmountRials targetAmountRials = converter.convert("1234");

        assertThat(targetAmountRials).isNotNull();
        assertThat(targetAmountRials.value()).isEqualTo(1_234L);
    }
}
