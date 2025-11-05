package ir.guru.payment.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ApplicationService
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class TransactionCreationService {
    private final TransactionFactory transactionFactory;
    private final TransactionRepository transactionRepository;

    Transaction createTransaction(TransactionImporter importer) throws TransactionCreationException {
        final var transaction = transactionFactory.createTransaction(importer);
        return transactionRepository.save(transaction);
    }
}
