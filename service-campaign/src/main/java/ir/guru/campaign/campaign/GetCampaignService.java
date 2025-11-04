package ir.guru.campaign.campaign;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class GetCampaignService {
    private final CampaignRepository campaignRepository;

    @Transactional(readOnly = true)
    Optional<CampaignXerox> findById(Long id) {
        return campaignRepository.findById(id).map(CampaignXerox::of);
    }

    @Transactional(readOnly = true)
    Set<CampaignXerox> filterCampaigns(CampaignFilter filter, Pageable pageable) {
        final var nameSpec = CampaignSpecifications.nameLike(filter.name());
        final var createdAtToSpec = CampaignSpecifications.createdAtTo(filter.createdAtTo());
        final var createdAtFromSpec = CampaignSpecifications.createdAtFrom(filter.createdAtFrom());

        final var spec = Specification.allOf(nameSpec, createdAtFromSpec, createdAtToSpec);
        return campaignRepository.findAll(spec, pageable).stream()
                .map(CampaignXerox::of)
                .collect(Collectors.toSet());
    }
}
