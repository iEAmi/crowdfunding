package ir.guru.payment.payment;

import static ir.guru.payment.payment.TransactionCreationException.duplicateTransactionException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class TransactionFactory {
    private final TransactionRepository transactionRepository;

    Transaction createTransaction(TransactionImporter importer) throws TransactionCreationException {
        guardAgainstDuplicateTransaction(importer);

        return Transaction.newTransaction(
                importer.amountRials(), importer.username(), importer.uniqueIdentifier(), importer.description());
    }

    private void guardAgainstDuplicateTransaction(TransactionImporter importer)
            throws TransactionCreationException.DuplicateTransactionException {
        final var uniqueIdentifier = importer.uniqueIdentifier();
        final var isDuplicateTransaction = transactionRepository.existsByUniqueIdentifier(uniqueIdentifier);

        if (isDuplicateTransaction) throw duplicateTransactionException(uniqueIdentifier);
    }
}
