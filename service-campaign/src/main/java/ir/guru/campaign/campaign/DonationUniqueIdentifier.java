package ir.guru.campaign.campaign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class DonationUniqueIdentifier {
    private String value;

    @JsonCreator
    public DonationUniqueIdentifier(String value) {
        if (value.trim().isEmpty()) throw new IllegalArgumentException("Donation uniqueIdentifier must not be blank");
        this.value = value.trim();
    }

    static DonationUniqueIdentifier random() {
        return new DonationUniqueIdentifier(UUID.randomUUID().toString());
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
