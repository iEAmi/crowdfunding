package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CurrentAmountRialsTest {

    @Test
    void ofRejectsNegativeValues() {
        assertThatThrownBy(() -> CurrentAmountRials.of(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exposesValueAndStringRepresentation() {
        CurrentAmountRials amount = CurrentAmountRials.of(2_500L);

        assertThat(amount.value()).isEqualTo(2_500L);
        assertThat(amount.toString()).isEqualTo("2500");
    }

    @Test
    void plusReturnsNewInstanceWithSummedValue() {
        CurrentAmountRials amount = CurrentAmountRials.of(1_000L);
        DonationAmountRials donation = DonationAmountRials.of(500L);

        CurrentAmountRials result = amount.plus(donation);

        assertThat(result.value()).isEqualTo(1_500L);
        assertThat(amount.value()).isEqualTo(1_000L); // immutability
    }
}
