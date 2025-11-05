package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DonationAmountRialsTest {

    @Test
    void ofRejectsNonPositiveValues() {
        assertThatThrownBy(() -> DonationAmountRials.of(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> DonationAmountRials.of(-50)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exposesValueAndStringRepresentation() {
        DonationAmountRials amount = DonationAmountRials.of(3_000L);

        assertThat(amount.value()).isEqualTo(3_000L);
        assertThat(amount.toString()).isEqualTo("3000");
    }
}
