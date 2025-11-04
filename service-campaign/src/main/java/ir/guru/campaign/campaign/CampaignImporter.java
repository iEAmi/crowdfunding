package ir.guru.campaign.campaign;

import org.springframework.lang.Nullable;

public interface CampaignImporter {
    CampaignName name();

    TargetAmountRials targetAmountRials();

    @Nullable String description();
}
