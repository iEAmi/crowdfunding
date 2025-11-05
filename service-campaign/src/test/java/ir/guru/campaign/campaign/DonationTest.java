package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class DonationTest {

    @Test
    void newInProgressCreatesDonationWithGeneratedIdentifier() {
        DonationAmountRials amount = DonationAmountRials.of(2_000L);
        Donation donation = Donation.newInProgress(15L, amount, "john");

        assertThat(donation.getCampaignId()).isEqualTo(15L);
        assertThat(donation.getAmountRials()).isEqualTo(amount);
        assertThat(donation.getUsername()).isEqualTo("john");
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.IN_PROGRESS);
        assertThat(donation.getUniqueIdentifier()).isNotNull();
        assertThat(donation.isPaid()).isFalse();
    }

    @Test
    void paidMarksDonationAsPaidAndSetsTimestamp() {
        Donation donation = Donation.newInProgress(1L, DonationAmountRials.of(100L), "kate");
        donation.paid();

        assertThat(donation.getStatus()).isEqualTo(DonationStatus.PAID);
        assertThat(donation.isPaid()).isTrue();
        assertThat(donation.getPaidAt()).isNotNull();
    }

    @Test
    void equalsIsBasedOnIdentifier() throws Exception {
        Donation first = Donation.newInProgress(1L, DonationAmountRials.of(100L), "alice");
        Donation second = Donation.newInProgress(1L, DonationAmountRials.of(200L), "bob");

        setField(first, Donation.Fields.id, 5L);
        setField(second, Donation.Fields.id, 5L);

        assertThat(first).isEqualTo(second);

        setField(second, Donation.Fields.id, 6L);
        assertThat(first).isNotEqualTo(second);
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
