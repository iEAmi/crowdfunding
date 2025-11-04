package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CreateCampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignXerox createCampaign(CampaignImporter importer) {
        Campaign campaign = Campaign.create(importer.name(), importer.description(), importer.targetAmountRials());

        final var saved = campaignRepository.save(campaign);

        return CampaignXerox.of(saved);
    }
}
