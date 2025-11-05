package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class DonationXeroxTest {

    @Test
    void ofCopiesFieldsFromDonationEntity() throws Exception {
        Donation donation = Donation.newInProgress(3L, DonationAmountRials.of(400L), "user");
        setField(donation, Donation.Fields.id, 21L);

        DonationXerox xerox = DonationXerox.of(donation);

        assertThat(xerox.id()).isEqualTo(21L);
        assertThat(xerox.username()).isEqualTo("user");
        assertThat(xerox.amountRials()).isEqualTo(donation.getAmountRials());
        assertThat(xerox.uniqueIdentifier()).isEqualTo(donation.getUniqueIdentifier());
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
