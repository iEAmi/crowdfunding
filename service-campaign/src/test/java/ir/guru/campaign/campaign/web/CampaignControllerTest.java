package ir.guru.campaign.campaign.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CampaignControllerTest {

    @Test
    void campaignResponseOfCopiesFields() {
        CampaignXerox xerox = new CampaignXerox(
                3L,
                new CampaignName("Relief"),
                "desc",
                TargetAmountRials.of(5_000L),
                CurrentAmountRials.of(2_000L),
                new TargetAmountReachedAt(LocalDateTime.parse("2024-04-02T10:00:00")),
                LocalDateTime.parse("2024-03-01T08:00:00"));

        CampaignController.CampaignResponse response = CampaignController.CampaignResponse.of(xerox);

        assertThat(response.id()).isEqualTo(3L);
        assertThat(response.name()).isEqualTo(xerox.name());
        assertThat(response.description()).isEqualTo("desc");
        assertThat(response.targetAmountRials()).isEqualTo(xerox.targetAmountRials());
        assertThat(response.currentAmountRials()).isEqualTo(xerox.currentAmountRials());
        assertThat(response.amountRialsReachedAt()).isEqualTo(xerox.amountRialsReachedAt());
        assertThat(response.createdAt()).isEqualTo(LocalDateTime.parse("2024-03-01T08:00:00"));
    }
}
