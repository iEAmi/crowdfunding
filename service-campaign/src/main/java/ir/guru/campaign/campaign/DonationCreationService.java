package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class DonationCreationService {
    private final DonationFactory donationFactory;
    private final DonationRepository donationRepository;

    void donate(Campaign campaign, String username, DonationAmountRials amountRials) throws DonationCreationException {
        final var donation = donationFactory.createDonation(campaign, username, amountRials);
        donationRepository.save(donation);
    }
}
