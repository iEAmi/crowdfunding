package ir.guru.campaign.campaign;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record CampaignFilter(
        @Nullable CampaignName name,
        @Nullable LocalDateTime createdAtFrom,
        @Nullable LocalDateTime createdAtTo) {}
