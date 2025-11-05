package ir.guru.campaign.campaign.infrastructure.persistence;

import ir.guru.campaign.campaign.CampaignName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.lang.Nullable;

@Converter(autoApply = true)
final class CampaignNameJpaConverter implements AttributeConverter<CampaignName, String> {

    @Override
    public @Nullable String convertToDatabaseColumn(@Nullable CampaignName attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public @Nullable CampaignName convertToEntityAttribute(@Nullable String dbData) {
        return dbData == null ? null : new CampaignName(dbData);
    }
}
