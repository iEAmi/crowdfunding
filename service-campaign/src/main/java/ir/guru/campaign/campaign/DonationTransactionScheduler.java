package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class DonationTransactionScheduler {
    private static final int PAGE_SIZE = 50;
    private final DonationRepository donationRepository;
    private final CampaignDonationService campaignDonateService;

    @Scheduled(
            initialDelayString = "${application.donation.in-progress.process.initialDelay}",
            fixedDelayString = "${application.donation.in-progress.process.fixedDelay}")
    void processInProgressDonation() {
        var page = Pageable.ofSize(PAGE_SIZE);

        Page<Donation> donations;

        do {
            donations = donationRepository.findByStatus(DonationStatus.IN_PROGRESS, page);
            page = page.next();
            processDonation(donations);
        } while (donations.getSize() < PAGE_SIZE);
    }

    private void processDonation(Page<Donation> donations) {
        for (final var donation : donations) processDonation(donation);
    }

    private void processDonation(Donation donation) {
        try {
            campaignDonateService.donate(donation);
        } catch (Exception e) {
            // Catch all exception, so no exception can break scheduling and create lage.
            log.atError().setCause(e).log("Exception in processing Donation: {}", donation.getId());
        }
    }
}
