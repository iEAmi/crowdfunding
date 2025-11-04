package ir.guru.payment.payment;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class GetTransactionService {
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    Optional<TransactionXerox> findById(Long id) {
        return transactionRepository.findById(id).map(TransactionXerox::of);
    }

    @Transactional(readOnly = true)
    Optional<TransactionXerox> findByUniqueIdentifier(TransactionUniqueIdentifier uniqueIdentifier) {
        return transactionRepository.findByUniqueIdentifier(uniqueIdentifier).map(TransactionXerox::of);
    }

    @Transactional(readOnly = true)
    Set<TransactionXerox> filterTransactions(TransactionFilter filter, Pageable pageable) {
        final var nameSpec = TransactionSpecifications.nameLike(filter.username());
        final var createdAtToSpec = TransactionSpecifications.createdAtTo(filter.createdAtTo());
        final var createdAtFromSpec = TransactionSpecifications.createdAtFrom(filter.createdAtFrom());

        final var spec = Specification.allOf(nameSpec, createdAtFromSpec, createdAtToSpec);
        return transactionRepository.findAll(spec, pageable).stream()
                .map(TransactionXerox::of)
                .collect(Collectors.toSet());
    }
}
