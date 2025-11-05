package ir.guru.campaign.campaign.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.TargetAmountRials;
import org.junit.jupiter.api.Test;

class TargetAmountRialsJpaConverterTest {

    private final TargetAmountRialsJpaConverter converter = new TargetAmountRialsJpaConverter();

    @Test
    void convertsEntityAttributeToDatabaseColumn() {
        TargetAmountRials amount = TargetAmountRials.of(1_200L);

        assertThat(converter.convertToDatabaseColumn(amount)).isEqualTo(1_200L);
    }

    @Test
    void convertsDatabaseColumnToEntityAttribute() {
        TargetAmountRials amount = converter.convertToEntityAttribute(1_500L);

        assertThat(amount.value()).isEqualTo(1_500L);
    }

    @Test
    void handlesNullValuesSafely() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
