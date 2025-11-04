package ir.guru.campaign.campaign.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.guru.campaign.campaign.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user/campaigns")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class CampaignController {
    private final CampaignFacade campaignFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateCampaignResponse createCampaign(@Valid @RequestBody CreateCampaignRequest request) {
        final var campaign = campaignFacade.createCampaign(request);
        return new CreateCampaignResponse(campaign.id());
    }

    @GetMapping("/{id}")
    ResponseEntity<CampaignResponse> findCampaign(@PathVariable("id") Long id) {
        final var campaign = campaignFacade.findById(id);
        return campaign.map(CampaignResponse::of).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound()
                .build());
    }

    @GetMapping
    Set<CampaignResponse> filterCampaigns(
            @RequestParam(name = "name", required = false) @Nullable CampaignName name,
            @RequestParam(name = "createdAtFrom", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    @Nullable
                    LocalDateTime createdAtFrom,
            @RequestParam(name = "createdAtTo", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    @Nullable
                    LocalDateTime createdAtTo,
            Pageable pageable) {
        final var filter = new CampaignFilter(name, createdAtFrom, createdAtTo);
        return campaignFacade.filterCampaigns(filter, pageable).stream()
                .map(CampaignResponse::of)
                .collect(Collectors.toSet());
    }

    private record CreateCampaignRequest(
            @NotNull(message = "user.campaign.createCampaign.null_name") @JsonProperty("name")
            CampaignName name,

            @JsonProperty("description") @Nullable String description,

            @NotNull(message = "user.campaign.createCampaign.null_targetAmountRials")
            @Positive(message = "user.campaign.createCampaign.negativeOrZero_targetAmountRials")
            @JsonProperty("targetAmountRials")
            TargetAmountRials targetAmountRials)
            implements CampaignImporter {}

    private record CreateCampaignResponse(
            @JsonProperty("id") Long id) {}

    private record CampaignResponse(
            @JsonProperty("id") Long id,
            @JsonProperty("name") CampaignName name,
            @JsonProperty("description") @Nullable String description,
            @JsonProperty("targetAmountRials") TargetAmountRials targetAmountRials,
            @JsonProperty("currentAmountRials") CurrentAmountRials currentAmountRials,
            @JsonProperty("amountRialsReachedAt") @Nullable TargetAmountReachedAt amountRialsReachedAt,
            @JsonProperty("createdAt") LocalDateTime createdAt) {

        private static CampaignResponse of(CampaignXerox xerox) {
            return new CampaignResponse(
                    xerox.id(),
                    xerox.name(),
                    xerox.description(),
                    xerox.targetAmountRials(),
                    xerox.currentAmountRials(),
                    xerox.amountRialsReachedAt(),
                    xerox.createdAt());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CampaignResponse that)) return false;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}
