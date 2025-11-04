package ir.guru.campaign.campaign;

import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

public record CampaignFilter(
        @Nullable CampaignName name,
        @Nullable LocalDateTime createdAtFrom,
        @Nullable LocalDateTime createdAtTo) {}
