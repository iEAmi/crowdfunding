package ir.guru.payment.payment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findByUniqueIdentifier(TransactionUniqueIdentifier uniqueIdentifier);

    boolean existsByUniqueIdentifier(TransactionUniqueIdentifier uniqueIdentifier);
}
