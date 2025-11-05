package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TargetAmountRialsTest {

    @Test
    void ofRejectsNonPositiveValues() {
        assertThatThrownBy(() -> TargetAmountRials.of(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> TargetAmountRials.of(-100)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exposesValueAndStringRepresentation() {
        TargetAmountRials rials = TargetAmountRials.of(50_000L);

        assertThat(rials.value()).isEqualTo(50_000L);
        assertThat(rials.toString()).isEqualTo("50000");
    }
}
