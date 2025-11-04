package ir.guru.payment.payment;

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
public final class TransactionUniqueIdentifier {
    private String value;

    // TODO: write test
    @JsonCreator
    public TransactionUniqueIdentifier(String value) {
        if (value.trim().isEmpty())
            throw new IllegalArgumentException("Transaction uniqueIdentifier must not be blank");
        this.value = value.trim();
    }

    // TODO: write test
    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
