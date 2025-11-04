package ir.guru.payment.payment;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

final class TransactionSpecifications {

    private TransactionSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    static Specification<Transaction> nameLike(@Nullable String name) {
        return UsernameSpec.of(name);
    }

    static Specification<Transaction> createdAtFrom(@Nullable LocalDateTime createAtFrom) {
        return CreatedAtFrom.of(createAtFrom);
    }

    static Specification<Transaction> createdAtTo(@Nullable LocalDateTime createAtTo) {
        return CreatedAtTo.of(createAtTo);
    }

    private record UsernameSpec(@Nullable String username) implements Specification<Transaction> {

        private static UsernameSpec of(@Nullable String name) {
            return new UsernameSpec(name);
        }

        @Override
        public @Nullable Predicate toPredicate(
                Root<Transaction> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (username == null) return null;

            final var namePath = root.get(Transaction.Fields.username).as(String.class);
            return cb.like(cb.lower(namePath), "%" + username + "%");
        }
    }

    private record CreatedAtFrom(@Nullable LocalDateTime createdAtFrom) implements Specification<Transaction> {

        private static CreatedAtFrom of(@Nullable LocalDateTime createdAtFrom) {
            return new CreatedAtFrom(createdAtFrom);
        }

        @Override
        public @Nullable Predicate toPredicate(
                Root<Transaction> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (createdAtFrom == null) return null;

            final var createdAtPath = root.get(Transaction.Fields.createdAt).as(LocalDateTime.class);
            return cb.greaterThanOrEqualTo(createdAtPath, createdAtFrom);
        }
    }

    private record CreatedAtTo(@Nullable LocalDateTime createdAtTo) implements Specification<Transaction> {
        private static CreatedAtTo of(@Nullable LocalDateTime createdAtTo) {
            return new CreatedAtTo(createdAtTo);
        }

        @Override
        public @Nullable Predicate toPredicate(
                Root<Transaction> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (createdAtTo == null) return null;

            final var createdAtPath = root.get(Transaction.Fields.createdAt).as(LocalDateTime.class);
            return cb.lessThanOrEqualTo(createdAtPath, createdAtTo);
        }
    }
}
