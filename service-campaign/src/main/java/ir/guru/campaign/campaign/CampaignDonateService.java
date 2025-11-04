package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignDonateService {

    void donate(Campaign campaign, String username, DonationAmountRials amountRials) {

    }
}
