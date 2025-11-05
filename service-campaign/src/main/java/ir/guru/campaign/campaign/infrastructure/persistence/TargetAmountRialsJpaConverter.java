package ir.guru.campaign.campaign.infrastructure.persistence;

import ir.guru.campaign.campaign.TargetAmountRials;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.lang.Nullable;

@Converter(autoApply = true)
final class TargetAmountRialsJpaConverter implements AttributeConverter<TargetAmountRials, Long> {

    @Override
    public @Nullable Long convertToDatabaseColumn(@Nullable TargetAmountRials attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public @Nullable TargetAmountRials convertToEntityAttribute(@Nullable Long dbData) {
        return dbData == null ? null : TargetAmountRials.of(dbData);
    }
}
