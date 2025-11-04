package ir.guru.campaign.campaign;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
interface DonationRepository extends CrudRepository<Donation, Long> {

    @Query("SELECT d.createdAt FROM Donation d ORDER BY d.createdAt DESC LIMIT 1")
    @Nullable
    LocalDateTime findLastDonationCreatedAtByUsername(String username);

}
