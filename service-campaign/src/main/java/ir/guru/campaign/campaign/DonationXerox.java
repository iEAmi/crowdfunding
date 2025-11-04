package ir.guru.campaign.campaign;

public record DonationXerox(
        Long id, String username, DonationAmountRials amountRials, DonationUniqueIdentifier uniqueIdentifier) {
    static DonationXerox of(Donation donation) {
        return new DonationXerox(
                donation.getId(), donation.getUsername(), donation.getAmountRials(), donation.getUniqueIdentifier());
    }
}
