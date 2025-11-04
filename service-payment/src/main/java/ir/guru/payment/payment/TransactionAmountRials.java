package ir.guru.payment.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class TransactionAmountRials {
    private long value;

    @JsonCreator
    public TransactionAmountRials(long value) {
        if (value <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        this.value = value;
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
