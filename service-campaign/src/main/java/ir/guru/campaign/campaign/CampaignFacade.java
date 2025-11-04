package ir.guru.campaign.campaign;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CampaignFacade {
    private final GetCampaignService getCampaignService;
    private final CreateCampaignService createCampaignService;

    public CampaignXerox createCampaign(CampaignImporter importer) {
        return createCampaignService.createCampaign(importer);
    }

    public Optional<CampaignXerox> findById(Long id) {
        return getCampaignService.findById(id);
    }

    public Set<CampaignXerox> filterCampaigns(CampaignFilter filter, Pageable pageable) {
        return getCampaignService.filterCampaigns(filter, pageable);
    }
}
