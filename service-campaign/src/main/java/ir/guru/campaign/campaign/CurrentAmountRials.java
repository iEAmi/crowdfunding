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
public final class CurrentAmountRials {
    static final CurrentAmountRials ZERO = new CurrentAmountRials(0);

    private long value;

    private CurrentAmountRials(long value) {
        if (value < 0) throw new IllegalArgumentException("Current amount must not be negative");
        this.value = value;
    }

    // TODO: write test
    @JsonCreator
    public static CurrentAmountRials of(long value) {
        return new CurrentAmountRials(value);
    }

    // TODO: write test
    @JsonValue
    public long value() {
        return value;
    }

    // TODO: write test
    CurrentAmountRials plus(DonationAmountRials donationAmountRials) {
        return new CurrentAmountRials(value + donationAmountRials.value());
    }

    // TODO: write test
    @Override
    public String toString() {
        return Long.toString(value);
    }
}
