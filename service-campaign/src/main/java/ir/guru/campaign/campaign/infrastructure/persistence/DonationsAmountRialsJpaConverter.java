package ir.guru.campaign.campaign.infrastructure.persistence;

import ir.guru.campaign.campaign.DonationAmountRials;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.lang.Nullable;

// TODO: write test
@Converter(autoApply = true)
final class DonationsAmountRialsJpaConverter implements AttributeConverter<DonationAmountRials, Long> {

    @Override
    public @Nullable Long convertToDatabaseColumn(@Nullable DonationAmountRials attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public @Nullable DonationAmountRials convertToEntityAttribute(@Nullable Long dbData) {
        if (dbData == null) return null;

        return DonationAmountRials.of(dbData);
    }
}
