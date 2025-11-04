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
public final class DonationAmountRials {
    private long value;

    private DonationAmountRials(long value) {
        if (value <= 0) throw new IllegalArgumentException("Donation amount must not be negative or zero");
        this.value = value;
    }

    // TODO: write test
    @JsonCreator
    public static DonationAmountRials of(long value) {
        return new DonationAmountRials(value);
    }

    // TODO: write test
    @JsonValue
    public long value() {
        return value;
    }

    // TODO: write test
    @Override
    public String toString() {
        return Long.toString(value);
    }
}
