package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CampaignFacade {
    private final GetCampaignService getCampaignService;
    private final CreateCampaignService createCampaignService;

    public CampaignXerox createCampaign(CampaignImporter importer) {
        return createCampaignService.createCampaign(importer);
    }

    @Transactional(readOnly = true)
    public Optional<CampaignXerox> findById(Long id) {
        return getCampaignService.findById(id);
    }

    @Transactional(readOnly = true)
    public Set<CampaignXerox> filterCampaigns(CampaignFilter filter, Pageable pageable) {
        return getCampaignService.filterCampaigns(filter, pageable);
    }
}
