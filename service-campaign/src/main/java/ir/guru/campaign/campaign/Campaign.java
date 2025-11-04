package ir.guru.campaign.campaign;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter(AccessLevel.PACKAGE)
@Entity(name = "Campaign")
@Table(name = "campaigns")
@FieldNameConstants(level = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "campaign_sequence", sequenceName = "campaign_sequence", allocationSize = 1)
class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private CampaignName name;

    @Column(name = "description", columnDefinition = "text")
    private @Nullable String description;

    @Column(name = "target_amount_rials", nullable = false)
    private TargetAmountRials targetAmountRials;

    @Column(name = "current_amount_rials", nullable = false)
    private CurrentAmountRials currentAmountRials;

    @Column(name = "target_amount_rials_reached_at")
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
}
