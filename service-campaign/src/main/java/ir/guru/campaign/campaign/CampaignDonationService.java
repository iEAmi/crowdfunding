package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

// ApplicationService
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignDonationService {
    private final CampaignDonator campaignDonator;
    private final CampaignRepository campaignRepository;
    private final DonationRepository donationRepository;
    private final TransactionOperations transactionOperations;
    private final DonationCreationService donationCreationService;

    void donate(Long campaignId, String username, DonationAmountRials amountRials) throws DonationCreationException {
        final var donation = donationCreationService.donate(campaignId, username, amountRials);

        donate(donation);
    }

    void donate(Donation donation) {
        final var campaign =
                campaignRepository.findById(donation.getCampaignId()).orElse(null);
        if (campaign == null) throw new IllegalStateException("Donation " + donation.getId() + " Campaign not found");

        campaignDonator.donate(campaign, donation);

        transactionOperations.executeWithoutResult(transaction -> {
            donationRepository.save(donation);
            campaignRepository.save(campaign);
        });
    }
}
