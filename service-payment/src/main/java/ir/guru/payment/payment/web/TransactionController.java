package ir.guru.payment.payment.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.payment.payment.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class TransactionController {
    private final TransactionFacade transactionFacade;

    @GetMapping("/{id}")
    ResponseEntity<TransactionResponse> findById(@PathVariable("id") Long id) {
        return transactionFacade
                .findById(id)
                .map(TransactionResponse::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/identifier/{uniqueIdentifier}")
    ResponseEntity<TransactionResponse> findByUniqueIdentifier(
            @PathVariable("uniqueIdentifier") TransactionUniqueIdentifier uniqueIdentifier) {
        return transactionFacade
                .findByUniqueIdentifier(uniqueIdentifier)
                .map(TransactionResponse::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    Set<TransactionResponse> listTransactions(
            @RequestParam(name = "username", required = false) @Nullable String username,
            @RequestParam(name = "createdAtFrom", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    @Nullable
                    LocalDateTime createdAtFrom,
            @RequestParam(name = "createdAtTo", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    @Nullable
                    LocalDateTime createdAtTo,
            Pageable pageable) {
        TransactionFilter filter = new TransactionFilter(username, createdAtFrom, createdAtTo);
        return transactionFacade.filterTransactions(filter, pageable).stream()
                .map(TransactionResponse::of)
                .collect(Collectors.toSet());
    }

    record TransactionResponse(
            @JsonProperty("id") Long id,
            @JsonProperty("amountRials") TransactionAmountRials amountRials,
            @JsonProperty("username") String username,
            @JsonProperty("uniqueIdentifier") TransactionUniqueIdentifier uniqueIdentifier,
            @JsonProperty("description") @Nullable String description,
            @JsonProperty("createdAt") LocalDateTime createdAt) {

        // TODO: write test
        static TransactionResponse of(TransactionXerox xerox) {
            return new TransactionResponse(
                    xerox.id(),
                    xerox.amountRials(),
                    xerox.username(),
                    xerox.uniqueIdentifier(),
                    xerox.description(),
                    xerox.createdAt());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof TransactionResponse that)) return false;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}
