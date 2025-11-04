package ir.guru.campaign.campaign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Embeddable
@EqualsAndHashCode
@FieldNameConstants(level = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class TargetAmountReachedAt {
    private LocalDateTime value;

    // TODO: write test
    @JsonCreator
    public TargetAmountReachedAt(LocalDateTime value) {
        this.value = value;
    }

    // TODO: write test
    @JsonCreator
    public TargetAmountReachedAt(String isoValue) {
        this.value = LocalDateTime.parse(isoValue, DateTimeFormatter.ISO_DATE_TIME);
    }

    // TODO: write test
    static TargetAmountReachedAt now() {
        return new TargetAmountReachedAt(LocalDateTime.now());
    }

    // TODO: write test
    public LocalDateTime value() {
        return value;
    }

    // TODO: write test
    @JsonValue
    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
