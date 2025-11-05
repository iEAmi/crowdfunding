package ir.guru.campaign.campaign.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.DonationAmountRials;
import org.junit.jupiter.api.Test;

class DonationsAmountRialsJpaConverterTest {

    private final DonationsAmountRialsJpaConverter converter = new DonationsAmountRialsJpaConverter();

    @Test
    void convertsEntityAttributeToDatabaseColumn() {
        DonationAmountRials amount = DonationAmountRials.of(600L);

        assertThat(converter.convertToDatabaseColumn(amount)).isEqualTo(600L);
    }

    @Test
    void convertsDatabaseColumnToEntityAttribute() {
        DonationAmountRials amount = converter.convertToEntityAttribute(750L);

        assertThat(amount.value()).isEqualTo(750L);
    }

    @Test
    void handlesNullValuesSafely() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
