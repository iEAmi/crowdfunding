package ir.guru.campaign.campaign;

import lombok.Getter;

public abstract sealed class DonationCreationException extends Exception {
    private DonationCreationException(String message) {
        super(message);
    }

    static DonationWindowViolationException donationWindowViolationException() {
        return new DonationWindowViolationException();
    }

    @Getter
    public static final class DonationWindowViolationException extends DonationCreationException {

        private DonationWindowViolationException() {
            super("Donation Window Violation");
        }
    }
}
