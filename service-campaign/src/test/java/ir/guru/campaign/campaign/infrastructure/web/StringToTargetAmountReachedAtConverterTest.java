package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.TargetAmountReachedAt;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class StringToTargetAmountReachedAtConverterTest {

    private final StringToTargetAmountReachedAtConverter converter = new StringToTargetAmountReachedAtConverter();

    @Test
    void convertsStringToTargetAmountReachedAt() {
        TargetAmountReachedAt reachedAt = converter.convert("2024-06-01T12:30:00");

        assertThat(reachedAt).isNotNull();
        assertThat(reachedAt.value()).isEqualTo(LocalDateTime.parse("2024-06-01T12:30:00"));
    }
}
