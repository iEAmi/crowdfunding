package ir.guru.campaign.campaign.web;

import static ir.guru.campaign.campaign.DonationCreationException.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.campaign.campaign.CampaignFacade;
import ir.guru.campaign.campaign.DonationAmountRials;
import ir.guru.campaign.campaign.DonationCreationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/user/campaigns")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignDonateController {
    private final CampaignFacade campaignFacade;

    @PutMapping("/{id}/donate")
    void donate(
            Authentication authentication,
            @PathVariable("id") Long campaignId,
            @Valid @RequestBody DonateRequest request) {
        String username = authentication.getName();
        try {
            campaignFacade.donate(campaignId, username, request.amountRials);
        } catch (DonationCreationException e) {
            throw handleException(e);
        }
    }

    private ResponseStatusException handleException(DonationCreationException e) {
        return switch (e) {
            case CampaignNotFoundException ee -> new ResponseStatusException(HttpStatus.NOT_FOUND, ee.getMessage());
            case InvalidDonationException ee -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ee.getMessage());
            case DonationWindowViolationException ee ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, ee.getMessage());
        };
    }

    private record DonateRequest(
            @JsonProperty("amountRials")
            @NotNull(message = "user.campaign.donate.null_amountRials")
            @Positive(message = "user.campaign.donate.negativeOrZero_amountRials")
            DonationAmountRials amountRials) {}
}
