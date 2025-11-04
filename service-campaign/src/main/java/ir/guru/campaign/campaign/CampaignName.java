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
public final class CampaignName {
    private String value;

    @JsonCreator
    public CampaignName(String value) {
        if (value.trim().isEmpty()) throw new IllegalArgumentException("Campaign name must not be blank");
        this.value = value.trim();
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
