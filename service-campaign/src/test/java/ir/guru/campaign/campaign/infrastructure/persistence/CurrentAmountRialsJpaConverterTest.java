package ir.guru.campaign.campaign.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.CurrentAmountRials;
import org.junit.jupiter.api.Test;

class CurrentAmountRialsJpaConverterTest {

    private final CurrentAmountRialsJpaConverter converter = new CurrentAmountRialsJpaConverter();

    @Test
    void convertsEntityAttributeToDatabaseColumn() {
        CurrentAmountRials amount = CurrentAmountRials.of(800L);

        assertThat(converter.convertToDatabaseColumn(amount)).isEqualTo(800L);
    }

    @Test
    void convertsDatabaseColumnToEntityAttribute() {
        CurrentAmountRials amount = converter.convertToEntityAttribute(900L);

        assertThat(amount.value()).isEqualTo(900L);
    }

    @Test
    void handlesNullValuesSafely() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
