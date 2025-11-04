package ir.guru.campaign.campaign.infrastructure.persistence;

import ir.guru.campaign.campaign.TargetAmountReachedAt;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

// TODO: write test
@Converter(autoApply = true)
final class TargetAmountReachedAtJpaConverter implements AttributeConverter<TargetAmountReachedAt, LocalDateTime> {

    @Override
    public @Nullable LocalDateTime convertToDatabaseColumn(@Nullable TargetAmountReachedAt attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public @Nullable TargetAmountReachedAt convertToEntityAttribute(@Nullable LocalDateTime dbData) {
        return dbData == null ? null : new TargetAmountReachedAt(dbData);
    }
}
