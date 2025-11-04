package ir.guru.campaign.campaign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Embeddable
@EqualsAndHashCode
@FieldNameConstants(level = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class DonationUniqueIdentifier {
    private String value;

    // TODO: write test
    @JsonCreator
    public DonationUniqueIdentifier(String value) {
        if (value.trim().isEmpty()) throw new IllegalArgumentException("Donation uniqueIdentifier must not be blank");
        this.value = value.trim();
    }

    // TODO: write test
    static DonationUniqueIdentifier random() {
        return new DonationUniqueIdentifier(UUID.randomUUID().toString());
    }

    // TODO: write test
    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
