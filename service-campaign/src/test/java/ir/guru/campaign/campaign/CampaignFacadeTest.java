package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CampaignFacadeTest {

    @Mock
    private GetCampaignService getCampaignService;

    @Mock
    private CreateCampaignService createCampaignService;

    @Mock
    private CampaignDonationService campaignDonationService;

    @Mock
    private CampaignImporter campaignImporter;

    @Mock
    private Pageable pageable;

    private CampaignFacade campaignFacade;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<CampaignFacade> constructor = CampaignFacade.class.getDeclaredConstructor(
                GetCampaignService.class, CreateCampaignService.class, CampaignDonationService.class);
        constructor.setAccessible(true);
        campaignFacade = constructor.newInstance(getCampaignService, createCampaignService, campaignDonationService);
    }

    @Test
    void createCampaignDelegatesToCreateService() {
        CampaignXerox xerox = mock(CampaignXerox.class);
        when(createCampaignService.createCampaign(campaignImporter)).thenReturn(xerox);

        assertThat(campaignFacade.createCampaign(campaignImporter)).isEqualTo(xerox);
        verify(createCampaignService).createCampaign(campaignImporter);
    }

    @Test
    void findByIdDelegatesToGetService() {
        CampaignXerox xerox = mock(CampaignXerox.class);
        when(getCampaignService.findById(4L)).thenReturn(Optional.of(xerox));

        assertThat(campaignFacade.findById(4L)).contains(xerox);
        verify(getCampaignService).findById(4L);
    }

    @Test
    void filterCampaignsDelegatesToGetService() {
        CampaignFilter filter = new CampaignFilter(null, null, null);
        CampaignXerox xerox = mock(CampaignXerox.class);
        when(getCampaignService.filterCampaigns(filter, pageable)).thenReturn(Set.of(xerox));

        assertThat(campaignFacade.filterCampaigns(filter, pageable)).containsExactly(xerox);
        verify(getCampaignService).filterCampaigns(filter, pageable);
    }

    @Test
    void donateDelegatesToCampaignDonationService() throws Exception {
        DonationAmountRials amount = DonationAmountRials.of(500L);

        campaignFacade.donate(1L, "user", amount);

        verify(campaignDonationService).donate(1L, "user", amount);
    }
}
