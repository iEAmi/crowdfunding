package ir.guru.campaign.campaign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Embeddable
@EqualsAndHashCode
@FieldNameConstants(level = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class TargetAmountRials {
    private long value;

    private TargetAmountRials(long value) {
        if (value <= 0) throw new IllegalArgumentException("Target amount must not be negative or zero");
        this.value = value;
    }

    @JsonCreator
    public static TargetAmountRials of(long value) {
        return new TargetAmountRials(value);
    }

    @JsonValue
    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
