package ir.guru.campaign.campaign;

import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface CampaignRepository extends JpaRepository<Campaign, Long>, JpaSpecificationExecutor<Campaign> {

    @EntityGraph(value = Campaign.Graphs.WITH_DONATIONS)
    Set<Campaign> findWithDonationsById(Long id);
}
