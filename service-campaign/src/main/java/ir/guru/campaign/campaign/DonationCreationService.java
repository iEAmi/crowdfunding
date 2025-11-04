package ir.guru.campaign.campaign;

import static ir.guru.campaign.campaign.DonationCreationException.campaignNotFoundException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ApplicationService
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class DonationCreationService {
    private final DonationFactory donationFactory;
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;

    Donation donate(Long campaignId, String username, DonationAmountRials amountRials)
            throws DonationCreationException {
        final var campaign = campaignRepository.findById(campaignId).orElse(null);
        if (campaign == null) throw campaignNotFoundException(campaignId);

        final var donation = donationFactory.createDonation(campaign, username, amountRials);
        return donationRepository.save(donation);
    }
}
