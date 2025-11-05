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
import org.springframework.lang.Nullable;

@Getter(AccessLevel.PACKAGE)
@Entity(name = "Donation")
@Table(name = "donations")
@FieldNameConstants(level = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "donation_sequence", sequenceName = "donation_sequence", allocationSize = 1)
class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "donation_sequence")
    private Long id;

    @Column(name = "campaign_id", nullable = false)
    private Long campaignId;

    @AttributeOverride(
            name = DonationAmountRials.Fields.value,
            column = @Column(name = "amount_rials", nullable = false))
    private DonationAmountRials amountRials;

    @Column(name = "username", nullable = false)
    private String username;

    @AttributeOverride(
            name = DonationUniqueIdentifier.Fields.value,
            column = @Column(name = "unique_identifier", nullable = false))
    private DonationUniqueIdentifier uniqueIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DonationStatus status;

    @CreationTimestamp
    @Column(name = "paid_at")
    private @Nullable LocalDateTime paidAt;

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
            Long campaignId,
            DonationAmountRials amountRials,
            String username,
            DonationUniqueIdentifier uniqueIdentifier,
            DonationStatus status) {
        this.campaignId = campaignId;
        this.amountRials = amountRials;
        this.username = username;
        this.uniqueIdentifier = uniqueIdentifier;
        this.status = status;

        final var now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    static Donation newInProgress(Long campaignId, DonationAmountRials donationAmountRials, String username) {
        final var uniqueIdentifier = DonationUniqueIdentifier.random();
        final var status = DonationStatus.IN_PROGRESS;
        return new Donation(campaignId, donationAmountRials, username, uniqueIdentifier, status);
    }

    void paid() {
        this.status = DonationStatus.PAID;
        this.paidAt = LocalDateTime.now();
    }

    boolean isPaid() {
        return this.status.equals(DonationStatus.PAID);
    }

    @Override
    public String toString() {
        return "Donation{" + "id="
                + id + ", campaignId="
                + campaignId + ", donationAmountRials="
                + amountRials + ", username='"
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
