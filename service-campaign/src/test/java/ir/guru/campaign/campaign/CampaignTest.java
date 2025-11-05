package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;

class CampaignTest {

    @Test
    void createInitializesCampaignWithZeroCurrentAmount() {
        CampaignName name = new CampaignName("Clean Water");
        TargetAmountRials targetAmountRials = TargetAmountRials.of(10_000L);

        Campaign campaign = Campaign.create(name, "desc", targetAmountRials);

        assertThat(campaign.getName()).isEqualTo(name);
        assertThat(campaign.getDescription()).isEqualTo("desc");
        assertThat(campaign.getTargetAmountRials()).isEqualTo(targetAmountRials);
        assertThat(campaign.getCurrentAmountRials().value()).isZero();
        assertThat(campaign.getTargetAmountRialsReachedAt()).isNull();
    }

    @Test
    void canDonateReturnsTrueWhenWithinRemainingAmount() {
        Campaign campaign = Campaign.create(new CampaignName("Tree Planting"), null, TargetAmountRials.of(1_000L));
        assertThat(campaign.canDonate(DonationAmountRials.of(500L))).isTrue();
    }

    @Test
    void canDonateReturnsFalseWhenTargetAlreadyReached() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Tree Planting"), null, TargetAmountRials.of(1_000L));
        setField(campaign, Campaign.Fields.currentAmountRials, CurrentAmountRials.of(1_000L));

        assertThat(campaign.canDonate(DonationAmountRials.of(100L))).isFalse();
    }

    @Test
    void canDonateReturnsFalseWhenDonationExceedsRemainingAmount() {
        Campaign campaign = Campaign.create(new CampaignName("Tree Planting"), null, TargetAmountRials.of(1_000L));
        campaign.donate(DonationAmountRials.of(800L));

        assertThat(campaign.canDonate(DonationAmountRials.of(300L))).isFalse();
    }

    @Test
    void donateAccumulatesAmountAndSetsTargetReachedAtWhenTargetMet() {
        Campaign campaign = Campaign.create(new CampaignName("Help"), null, TargetAmountRials.of(1_000L));

        campaign.donate(DonationAmountRials.of(400L));
        assertThat(campaign.getCurrentAmountRials().value()).isEqualTo(400L);
        assertThat(campaign.getTargetAmountRialsReachedAt()).isNull();

        campaign.donate(DonationAmountRials.of(600L));
        assertThat(campaign.getCurrentAmountRials().value()).isEqualTo(1_000L);
        assertThat(campaign.getTargetAmountRialsReachedAt()).isNotNull();
    }

    @Test
    void equalsIsBasedOnId() throws Exception {
        Campaign first = Campaign.create(new CampaignName("First"), null, TargetAmountRials.of(100L));
        Campaign second = Campaign.create(new CampaignName("Second"), null, TargetAmountRials.of(200L));

        setField(first, Campaign.Fields.id, 11L);
        setField(second, Campaign.Fields.id, 11L);

        assertThat(first).isEqualTo(second);

        setField(second, Campaign.Fields.id, 12L);
        assertThat(first).isNotEqualTo(second);
    }

    private static void setField(Object target, String fieldName, @Nullable Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
