package ir.guru.campaign.campaign.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.campaign.campaign.CampaignFacade;
import ir.guru.campaign.campaign.DonationAmountRials;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/campaigns")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignDonateController {
    private final CampaignFacade campaignFacade;

    @PutMapping("/{id}/donate")
    void donate(@PathVariable("id") Long campaignId, @Valid @RequestBody DonateRequest request) {}

    private record DonateRequest(
            @JsonProperty("amountRials")
            @NotNull(message = "user.campaign.donate.null_amountRials")
            @Positive(message = "user.campaign.donate.negativeOrZero_amountRials")
            DonationAmountRials amountRials) {}
}
