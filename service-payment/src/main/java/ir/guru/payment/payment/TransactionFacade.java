package ir.guru.payment.payment;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionFacade {
    private final GetTransactionService getTransactionService;

    public Optional<TransactionXerox> findById(Long id) {
        return getTransactionService.findById(id);
    }

    public Optional<TransactionXerox> findByUniqueIdentifier(TransactionUniqueIdentifier uniqueIdentifier) {
        return getTransactionService.findByUniqueIdentifier(uniqueIdentifier);
    }

    public Set<TransactionXerox> filterTransactions(TransactionFilter filter, Pageable pageable) {
        return getTransactionService.filterTransactions(filter, pageable);
    }
}
