package ir.guru.campaign.campaign.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.TargetAmountReachedAt;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TargetAmountReachedAtJpaConverterTest {

    private final TargetAmountReachedAtJpaConverter converter = new TargetAmountReachedAtJpaConverter();

    @Test
    void convertsEntityAttributeToDatabaseColumn() {
        TargetAmountReachedAt reachedAt = new TargetAmountReachedAt(LocalDateTime.parse("2024-06-02T14:00:00"));

        assertThat(converter.convertToDatabaseColumn(reachedAt)).isEqualTo(LocalDateTime.parse("2024-06-02T14:00:00"));
    }

    @Test
    void convertsDatabaseColumnToEntityAttribute() {
        TargetAmountReachedAt reachedAt =
                converter.convertToEntityAttribute(LocalDateTime.parse("2024-07-03T15:00:00"));

        assertThat(reachedAt.value()).isEqualTo(LocalDateTime.parse("2024-07-03T15:00:00"));
    }

    @Test
    void handlesNullValuesSafely() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
