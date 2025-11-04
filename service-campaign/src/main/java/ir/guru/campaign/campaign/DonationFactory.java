package ir.guru.campaign.campaign;

import static ir.guru.campaign.campaign.DonationCreationException.donationWindowViolationException;
import static ir.guru.campaign.campaign.DonationCreationException.invalidDonationException;

import java.time.Duration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class DonationFactory {
    private final DonationProperties properties;
    private final DonationRepository donationRepository;

    Donation createDonation(Campaign campaign, String username, DonationAmountRials amountRials)
            throws DonationCreationException {
        guardAgainstInvalidDonation(campaign, amountRials);

        final var donation = Donation.newInProgress(campaign.getId(), amountRials, username);

        guardAgainstDonationWindowViolation(donation);

        return donation;
    }

    private void guardAgainstInvalidDonation(Campaign campaign, DonationAmountRials amountRials)
            throws DonationCreationException.InvalidDonationException {
        if (!campaign.canDonate(amountRials)) throw invalidDonationException(amountRials);
    }

    private void guardAgainstDonationWindowViolation(Donation donation)
            throws DonationCreationException.DonationWindowViolationException {
        final var lastDonationCreatedAt =
                donationRepository.findLastDonationCreatedAtByUsername(donation.getUsername());
        if (lastDonationCreatedAt == null) return;

        final var window = properties.donationWindow();

        final var diff = Duration.between(donation.getCreatedAt(), lastDonationCreatedAt);
        if (diff.minus(window).isNegative()) throw donationWindowViolationException();
    }
}
