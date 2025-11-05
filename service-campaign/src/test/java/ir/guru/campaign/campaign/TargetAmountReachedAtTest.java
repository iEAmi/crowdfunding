package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TargetAmountReachedAtTest {

    @Test
    void storesProvidedLocalDateTime() {
        LocalDateTime time = LocalDateTime.parse("2024-01-01T10:00:00");

        TargetAmountReachedAt reachedAt = new TargetAmountReachedAt(time);

        assertThat(reachedAt.value()).isEqualTo(time);
        assertThat(reachedAt.toString()).isEqualTo("2024-01-01T10:00:00");
    }

    @Test
    void parsesIsoFormattedString() {
        TargetAmountReachedAt reachedAt = new TargetAmountReachedAt("2024-02-20T15:30:00");

        assertThat(reachedAt.value()).isEqualTo(LocalDateTime.parse("2024-02-20T15:30:00"));
    }

    @Test
    void nowReturnsCurrentTimestamp() {
        LocalDateTime before = LocalDateTime.now();
        TargetAmountReachedAt reachedAt = TargetAmountReachedAt.now();
        LocalDateTime after = LocalDateTime.now();

        assertThat(Duration.between(before, reachedAt.value()).isNegative()).isFalse();
        assertThat(Duration.between(reachedAt.value(), after).isNegative()).isFalse();
    }
}
