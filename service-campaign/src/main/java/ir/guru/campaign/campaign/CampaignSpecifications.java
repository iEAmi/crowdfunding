package ir.guru.campaign.campaign;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

final class CampaignSpecifications {
    private CampaignSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    static Specification<Campaign> nameLike(@Nullable CampaignName name) {
        return NameSpec.of(name);
    }

    static Specification<Campaign> createdAtFrom(@Nullable LocalDateTime createAtFrom) {
        return CreatedAtFrom.of(createAtFrom);
    }

    static Specification<Campaign> createdAtTo(@Nullable LocalDateTime createAtTo) {
        return CreatedAtTo.of(createAtTo);
    }

    private record NameSpec(@Nullable CampaignName name) implements Specification<Campaign> {

        private static NameSpec of(@Nullable CampaignName name) {
            return new NameSpec(name);
        }

        @Override
        public @Nullable Predicate toPredicate(Root<Campaign> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (name == null) return null;

            final var namePath = root.get(Campaign.Fields.name).as(String.class);
            return cb.like(cb.lower(namePath), "%" + name + "%");
        }
    }

    private record CreatedAtFrom(@Nullable LocalDateTime createdAtFrom) implements Specification<Campaign> {

        private static CreatedAtFrom of(@Nullable LocalDateTime createdAtFrom) {
            return new CreatedAtFrom(createdAtFrom);
        }

        @Override
        public @Nullable Predicate toPredicate(Root<Campaign> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (createdAtFrom == null) return null;

            final var createdAtPath = root.get(Campaign.Fields.createdAt).as(LocalDateTime.class);
            return cb.greaterThanOrEqualTo(createdAtPath, createdAtFrom);

        }
    }

    private record CreatedAtTo(@Nullable LocalDateTime createdAtTo) implements Specification<Campaign> {
        private static CreatedAtTo of(@Nullable LocalDateTime createdAtTo) {
            return new CreatedAtTo(createdAtTo);
        }

        @Override
        public @Nullable Predicate toPredicate(Root<Campaign> root, @Nullable CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (createdAtTo == null) return null;

            final var createdAtPath = root.get(Campaign.Fields.createdAt).as(LocalDateTime.class);
            return cb.lessThanOrEqualTo(createdAtPath, createdAtTo);
        }
    }
}
