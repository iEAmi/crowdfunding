package ir.guru.campaign.campaign.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.campaign.campaign.DonationAmountRials;
import ir.guru.campaign.campaign.DonationUniqueIdentifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "paymentClient", url = "${application.payment-service.base-url}")
interface PaymentServiceClient {

    @PostMapping(
            value = "${application.payment-service.create-transaction-url}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);

    record CreateTransactionRequest(
            @JsonProperty("username") String username,

            @JsonProperty("description") @Nullable String description,

            @JsonProperty("amountRials") DonationAmountRials amountRials,

            @JsonProperty("uniqueIdentifier") DonationUniqueIdentifier uniqueIdentifier) {}

    record CreateTransactionResponse(@JsonProperty("id") Long id) {}
}
