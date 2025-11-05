package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DonationFactoryTest {

    @Mock
    private DonationRepository donationRepository;

    private DonationFactory donationFactory;

    private final DonationProperties properties = new DonationProperties(Duration.ofMinutes(1));

    @BeforeEach
    void setUp() throws Exception {
        Constructor<DonationFactory> constructor =
                DonationFactory.class.getDeclaredConstructor(DonationProperties.class, DonationRepository.class);
        constructor.setAccessible(true);
        donationFactory = constructor.newInstance(properties, donationRepository);
    }

    @Test
    void createDonationReturnsNewDonationWhenValid() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Books"), null, TargetAmountRials.of(2_000L));

        when(donationRepository.findLastDonationCreatedAtByUsername("user")).thenReturn(null);

        Donation donation = donationFactory.createDonation(campaign, "user", DonationAmountRials.of(500L));

        assertThat(donation.getCampaignId()).isEqualTo(campaign.getId());
        assertThat(donation.getAmountRials().value()).isEqualTo(500L);
        assertThat(donation.getUsername()).isEqualTo("user");
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.IN_PROGRESS);
        verify(donationRepository).findLastDonationCreatedAtByUsername("user");
    }

    @Test
    void createDonationThrowsWhenCampaignCannotAcceptDonation() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Books"), null, TargetAmountRials.of(2_000L));
        setField(campaign, Campaign.Fields.currentAmountRials, CurrentAmountRials.of(2_000L));

        assertThatThrownBy(() -> donationFactory.createDonation(campaign, "user", DonationAmountRials.of(100L)))
                .isInstanceOf(DonationCreationException.InvalidDonationException.class);
    }

    @Test
    void createDonationThrowsWhenDonationWindowViolationDetected() throws Exception {
        Campaign campaign = Campaign.create(new CampaignName("Books"), null, TargetAmountRials.of(2_000L));

        when(donationRepository.findLastDonationCreatedAtByUsername("user"))
                .thenReturn(LocalDateTime.now().minusSeconds(5));

        assertThatThrownBy(() -> donationFactory.createDonation(campaign, "user", DonationAmountRials.of(500L)))
                .isInstanceOf(DonationCreationException.DonationWindowViolationException.class);
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
