package ir.guru.payment.payment.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.payment.payment.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CreateTransactionController {
    private final TransactionFacade transactionFacade;

    @PostMapping
    CreateTransactionResponse createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        try {
            final var transaction = transactionFacade.createTransaction(request);
            return new CreateTransactionResponse(transaction.id());
        } catch (TransactionCreationException e) {
            throw handleException(e);
        }
    }

    private ResponseStatusException handleException(TransactionCreationException e) {
        return switch (e) {
            case TransactionCreationException.DuplicateTransactionException ee ->
                new ResponseStatusException(HttpStatus.CONFLICT, ee.getMessage());
        };
    }

    private record CreateTransactionRequest(
            @JsonProperty("username")
            @NotNull(message = "transaction.create.null_username")
            @NotBlank(message = "transaction.create.blank_username")
            String username,

            @JsonProperty("description") @Size(max = 300, message = "transaction.create.large_description") @Nullable
            String description,

            @JsonProperty("amountRials")
            @NotNull(message = "transaction.create.null_amountRials")
            @Positive(message = "transaction.create.negativeOrZero_amountRials")
            TransactionAmountRials amountRials,

            @JsonProperty("uniqueIdentifier")
            @NotNull(message = "transaction.create.null_uniqueIdentifier")
            @Size(min = 5, max = 100, message = "transaction.create.invalidSize_uniqueIdentifier")
            TransactionUniqueIdentifier uniqueIdentifier)
            implements TransactionImporter {}

    private record CreateTransactionResponse(
            @JsonProperty("id") Long id) {}
}
