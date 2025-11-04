package ir.guru.campaign.campaign;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

@Getter(AccessLevel.PACKAGE)
@Entity(name = "Campaign")
@Table(name = "campaigns")
@FieldNameConstants(level = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "campaign_sequence", sequenceName = "campaign_sequence", allocationSize = 1)
class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence")
    private Long id;

    @AttributeOverride(name = CampaignName.Fields.value, column = @Column(name = "name", nullable = false, length = 255))
    private CampaignName name;

    @Column(name = "description", columnDefinition = "text")
    private @Nullable String description;

    @AttributeOverride(name = TargetAmountRials.Fields.value, column = @Column(name = "target_amount_rials", nullable = false))
    private TargetAmountRials targetAmountRials;

    @AttributeOverride(name = CurrentAmountRials.Fields.value, column = @Column(name = "current_amount_rials", nullable = false))
    private CurrentAmountRials currentAmountRials;

    @AttributeOverride(name = TargetAmountReachedAt.Fields.value, column = @Column(name = "target_amount_rials_reached_at"))
    private @Nullable TargetAmountReachedAt targetAmountRialsReachedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    private Campaign(CampaignName name, @Nullable String description, TargetAmountRials targetAmountRials) {
        this.name = name;
        this.description = description;
        this.targetAmountRials = targetAmountRials;
        this.currentAmountRials = CurrentAmountRials.ZERO;
        this.targetAmountRialsReachedAt = null;
    }

    static Campaign create(CampaignName name, @Nullable String description, TargetAmountRials targetAmountRials) {
        return new Campaign(name, description, targetAmountRials);
    }

    boolean canDonate(DonationAmountRials donationAmountRials) {
        if (isTargetReached()) return false;

        return getRemainingAmountRials() > donationAmountRials.value();
    }

    void donate(DonationAmountRials donationAmountRials) {
        this.currentAmountRials = currentAmountRials.plus(donationAmountRials);

        if (isTargetReached()) this.targetAmountRialsReachedAt = TargetAmountReachedAt.now();
    }

    private long getRemainingAmountRials() {
        return targetAmountRials.value() - currentAmountRials.value();
    }

    private boolean isTargetReached() {
        return this.targetAmountRials.value() == this.currentAmountRials.value();
    }

    @Override
    public String toString() {
        return "Campaign{" + "id="
                + id + ", name="
                + name + ", description='"
                + description + '\'' + ", targetAmountRials="
                + targetAmountRials + ", currentAmountRials="
                + currentAmountRials + ", targetAmountRialsReachedAt="
                + targetAmountRialsReachedAt + ", createdAt="
                + createdAt + ", updatedAt="
                + updatedAt + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Campaign campaign)) return false;
        return Objects.equals(id, campaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
