package ir.guru.campaign.campaign;

import lombok.Getter;

public abstract sealed class DonationCreationException extends Exception {
    private DonationCreationException(String message) {
        super(message);
    }

    static DonationWindowViolationException donationWindowViolationException() {
        return new DonationWindowViolationException();
    }

    static InvalidDonationException invalidDonationException(DonationAmountRials amountRials) {
        return new InvalidDonationException(amountRials);
    }

    static CampaignNotFoundException campaignNotFoundException(Long campaignId) {
        return new CampaignNotFoundException(campaignId);
    }

    @Getter
    public static final class InvalidDonationException extends DonationCreationException {
        private final DonationAmountRials amountRials;

        private InvalidDonationException(DonationAmountRials amountRials) {
            super("Invalid Donation Amount:" + amountRials);
            this.amountRials = amountRials;
        }
    }

    @Getter
    public static final class DonationWindowViolationException extends DonationCreationException {

        private DonationWindowViolationException() {
            super("Donation Window Violation");
        }
    }

    @Getter
    public static final class CampaignNotFoundException extends DonationCreationException {
        private final Long campaignId;

        private CampaignNotFoundException(Long campaignId) {
            super("Campaign not found for id:" + campaignId);
            this.campaignId = campaignId;
        }
    }
}
