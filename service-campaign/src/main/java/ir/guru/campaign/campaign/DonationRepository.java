package ir.guru.campaign.campaign;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
interface DonationRepository extends CrudRepository<Donation, Long> {

    @Query("SELECT d.createdAt FROM Donation d ORDER BY d.createdAt DESC LIMIT 1")
    @Nullable
    LocalDateTime findLastDonationCreatedAtByUsername(String username);

    Page<Donation> findByStatus(DonationStatus status, Pageable pageable);
}
