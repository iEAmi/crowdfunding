package ir.guru.campaign.campaign.infrastructure.persistence;

import ir.guru.campaign.campaign.CurrentAmountRials;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.lang.Nullable;

@Converter(autoApply = true)
final class CurrentAmountRialsJpaConverter implements AttributeConverter<CurrentAmountRials, Long> {

    @Override
    public @Nullable Long convertToDatabaseColumn(@Nullable CurrentAmountRials attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public @Nullable CurrentAmountRials convertToEntityAttribute(@Nullable Long dbData) {
        if (dbData == null) return null;

        return CurrentAmountRials.of(dbData);
    }
}
