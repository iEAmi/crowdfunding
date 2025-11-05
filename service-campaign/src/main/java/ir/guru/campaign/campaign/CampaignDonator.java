package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DomainService
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignDonator {
    private final TransactionCreator transactionCreator;

    void donate(Campaign campaign, Donation donation) {
        final var createTransactionResult = createTransaction(donation);

        switch (createTransactionResult) {
            case SUCCESS, DUPLICATE_TRANSACTION -> {
                donation.paid();
                campaign.donate(donation.getAmountRials());
            }
            case UNKNOWN_EXCEPTION -> {
                /* Do nothing. try in next schedule */
            }
        }
    }

    private CreateTransactionResult createTransaction(Donation donation) {
        final var donationXerox = DonationXerox.of(donation);
        try {
            transactionCreator.createTransaction(donationXerox);
            return CreateTransactionResult.SUCCESS;
        } catch (TransactionCreator.TransactionCreationException e) {
            return switch (e) {
                case TransactionCreator.TransactionCreationException.DuplicateTransactionException _ ->
                    CreateTransactionResult.DUPLICATE_TRANSACTION;
                case TransactionCreator.TransactionCreationException.UnknownTransactionException _ ->
                    CreateTransactionResult.UNKNOWN_EXCEPTION;
            };
        }
    }

    private enum CreateTransactionResult {
        SUCCESS,
        UNKNOWN_EXCEPTION,
        DUPLICATE_TRANSACTION,
    }
}
