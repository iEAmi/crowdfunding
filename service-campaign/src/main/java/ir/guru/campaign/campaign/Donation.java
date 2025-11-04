package ir.guru.campaign.campaign;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter(AccessLevel.PACKAGE)
@Entity(name = "Donation")
@Table(name = "donations")
@FieldNameConstants(level = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "donation_sequence", sequenceName = "donation_sequence", allocationSize = 1)
class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence")
    private Long id;

    @Column(name = "donation_amount_rials", nullable = false)
    private DonationAmountRials donationAmountRials;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "unique_identifier", nullable = false)
    private DonationUniqueIdentifier uniqueIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DonationStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    private Donation(
            DonationAmountRials donationAmountRials,
            String username,
            DonationUniqueIdentifier uniqueIdentifier,
            DonationStatus status) {
        this.donationAmountRials = donationAmountRials;
        this.username = username;
        this.uniqueIdentifier = uniqueIdentifier;
        this.status = status;
    }

    static Donation newInProgress(DonationAmountRials donationAmountRials, String username) {
        final var uniqueIdentifier = DonationUniqueIdentifier.random();
        final var status = DonationStatus.IN_PROGRESS;
        return new Donation(donationAmountRials, username, uniqueIdentifier, status);
    }

    @Override
    public String toString() {
        return "Donation{" + "id="
                + id + ", donationAmountRials="
                + donationAmountRials + ", username='"
                + username + '\'' + ", uniqueIdentifier="
                + uniqueIdentifier + ", status="
                + status + ", createdAt="
                + createdAt + ", updatedAt="
                + updatedAt + ", version="
                + version + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Donation donation)) return false;
        return Objects.equals(id, donation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
