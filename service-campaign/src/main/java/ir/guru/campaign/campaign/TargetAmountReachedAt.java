package ir.guru.campaign.campaign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class TargetAmountReachedAt {
    private LocalDateTime value;

    @JsonCreator
    public TargetAmountReachedAt(LocalDateTime value) {
        this.value = value;
    }

    @JsonCreator
    public TargetAmountReachedAt(String isoValue) {
        this.value = LocalDateTime.parse(isoValue, DateTimeFormatter.ISO_DATE_TIME);
    }

    public LocalDateTime value() {
        return value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
