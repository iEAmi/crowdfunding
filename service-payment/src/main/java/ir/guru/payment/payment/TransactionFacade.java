package ir.guru.payment.payment;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// ApplicationService
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionFacade {
    private final GetTransactionService getTransactionService;
    private final TransactionCreationService transactionCreationService;

    // TODO: write test, behavioral test
    public Optional<TransactionXerox> findById(Long id) {
        return getTransactionService.findById(id);
    }

    // TODO: write test, behavioral test
    public Optional<TransactionXerox> findByUniqueIdentifier(TransactionUniqueIdentifier uniqueIdentifier) {
        return getTransactionService.findByUniqueIdentifier(uniqueIdentifier);
    }

    // TODO: write test, behavioral test
    public Set<TransactionXerox> filterTransactions(TransactionFilter filter, Pageable pageable) {
        return getTransactionService.filterTransactions(filter, pageable);
    }

    // TODO: write test, behavioral test
    public TransactionXerox createTransaction(TransactionImporter importer) throws TransactionCreationException {
        final var transaction = transactionCreationService.createTransaction(importer);

        return TransactionXerox.of(transaction);
    }
}
