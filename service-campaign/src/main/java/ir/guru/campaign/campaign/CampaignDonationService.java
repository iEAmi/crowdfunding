package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignDonationService {
    private final CampaignDonator campaignDonator;
    private final CampaignRepository campaignRepository;
    private final DonationRepository donationRepository;
    private final TransactionOperations transactionOperations;

    void donate(Donation donation) {
        Campaign campaign = campaignRepository.findById(donation.getId()).orElse(null);
        if (campaign == null) throw new IllegalStateException("Donation " + donation.getId() + " Campaign not found");

        campaignDonator.donate(campaign, donation);

        transactionOperations.executeWithoutResult(transaction -> {
            donationRepository.save(donation);
            campaignRepository.save(campaign);
        });
    }
}
