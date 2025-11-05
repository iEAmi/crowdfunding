package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CampaignDonatorTest {

    @Mock
    private TransactionCreator transactionCreator;

    private CampaignDonator campaignDonator;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<CampaignDonator> constructor =
                CampaignDonator.class.getDeclaredConstructor(TransactionCreator.class);
        constructor.setAccessible(true);
        campaignDonator = constructor.newInstance(transactionCreator);
    }

    @Test
    void donateMarksDonationAsPaidWhenTransactionCreationSucceeds() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Help"), null, TargetAmountRials.of(3_000L));
        Donation donation = Donation.newInProgress(1L, DonationAmountRials.of(700L), "user");
        setField(donation, Donation.Fields.id, 25L);

        campaignDonator.donate(campaign, donation);

        assertThat(donation.isPaid()).isTrue();
        assertThat(campaign.getCurrentAmountRials().value()).isEqualTo(700L);
        verify(transactionCreator).createTransaction(any(DonationXerox.class));
    }

    @Test
    void donateTreatsDuplicateTransactionAsSuccessful() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Help"), null, TargetAmountRials.of(3_000L));
        Donation donation = Donation.newInProgress(1L, DonationAmountRials.of(700L), "user");
        setField(donation, Donation.Fields.id, 26L);

        doAnswer(invocation -> {
                    DonationXerox xerox = invocation.getArgument(0);
                    throw TransactionCreator.TransactionCreationException.duplicateTransactionException(xerox);
                })
                .when(transactionCreator)
                .createTransaction(any(DonationXerox.class));

        campaignDonator.donate(campaign, donation);

        assertThat(donation.isPaid()).isTrue();
        assertThat(campaign.getCurrentAmountRials().value()).isEqualTo(700L);
    }

    @Test
    void donateKeepsDonationInProgressWhenUnknownExceptionOccurs() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Help"), null, TargetAmountRials.of(3_000L));
        Donation donation = Donation.newInProgress(1L, DonationAmountRials.of(700L), "user");
        setField(donation, Donation.Fields.id, 27L);

        doAnswer(invocation -> {
                    DonationXerox xerox = invocation.getArgument(0);
                    throw TransactionCreator.TransactionCreationException.unknownTransactionException(xerox);
                })
                .when(transactionCreator)
                .createTransaction(any(DonationXerox.class));

        campaignDonator.donate(campaign, donation);

        assertThat(donation.isPaid()).isFalse();
        assertThat(campaign.getCurrentAmountRials().value()).isZero();
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
