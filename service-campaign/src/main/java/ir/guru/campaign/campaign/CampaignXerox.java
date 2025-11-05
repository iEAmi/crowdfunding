package ir.guru.campaign.campaign;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.lang.Nullable;

public record CampaignXerox(
        Long id,
        CampaignName name,
        @Nullable String description,
        TargetAmountRials targetAmountRials,
        CurrentAmountRials currentAmountRials,
        @Nullable TargetAmountReachedAt amountRialsReachedAt,
        LocalDateTime createdAt) {

    static CampaignXerox of(Campaign campaign) {
        return new CampaignXerox(
                campaign.getId(),
                campaign.getName(),
                campaign.getDescription(),
                campaign.getTargetAmountRials(),
                campaign.getCurrentAmountRials(),
                campaign.getTargetAmountRialsReachedAt(),
                campaign.getCreatedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CampaignXerox that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
