package ir.guru.campaign.campaign.infrastructure;

import ir.guru.campaign.campaign.DonationXerox;
import ir.guru.campaign.campaign.TransactionCreator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class PaymentServiceTransactionCreator implements TransactionCreator {
    @Override
    public void createTransaction(DonationXerox donation) throws TransactionCreationException {}
}
