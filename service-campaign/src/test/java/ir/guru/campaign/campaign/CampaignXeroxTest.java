package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CampaignXeroxTest {

    @Test
    void ofCopiesFieldsFromCampaign() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Education"), "desc", TargetAmountRials.of(2_000L));
        setField(campaign, Campaign.Fields.id, 9L);
        setField(campaign, Campaign.Fields.createdAt, LocalDateTime.parse("2024-05-01T10:00:00"));

        CampaignXerox xerox = CampaignXerox.of(campaign);

        assertThat(xerox.id()).isEqualTo(9L);
        assertThat(xerox.name()).isEqualTo(campaign.getName());
        assertThat(xerox.description()).isEqualTo("desc");
        assertThat(xerox.targetAmountRials()).isEqualTo(campaign.getTargetAmountRials());
        assertThat(xerox.currentAmountRials()).isEqualTo(campaign.getCurrentAmountRials());
        assertThat(xerox.amountRialsReachedAt()).isEqualTo(campaign.getTargetAmountRialsReachedAt());
        assertThat(xerox.createdAt()).isEqualTo(LocalDateTime.parse("2024-05-01T10:00:00"));
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
