package ir.guru.campaign.campaign.infrastructure;

import feign.FeignException;
import ir.guru.campaign.campaign.DonationXerox;
import ir.guru.campaign.campaign.TransactionCreator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class PaymentServiceTransactionCreator implements TransactionCreator {
    private final PaymentServiceClient paymentServiceClient;

    @Override
    public void createTransaction(DonationXerox donation) throws TransactionCreationException {
        final var description = String.format("Donation '%s' transaction", donation.id());
        final var username = donation.username();
        final var amountRials = donation.amountRials();
        final var uniqueIdentifier = donation.uniqueIdentifier();
        final var request =
                new PaymentServiceClient.CreateTransactionRequest(username, description, amountRials, uniqueIdentifier);

        try {
            final var _ = paymentServiceClient.createTransaction(request);
        } catch (FeignException.Conflict e) {
            throw TransactionCreationException.duplicateTransactionException(donation);
        } catch (FeignException ex) {
            throw TransactionCreationException.unknownTransactionException(donation);
        }
    }
}
