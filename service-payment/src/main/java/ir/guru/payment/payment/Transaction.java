package ir.guru.payment.payment;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    private Long id;

    @Column(name = "amount_rials", nullable = false)
    private TransactionAmountRials amountRials;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "unique_identifier", nullable = false, unique = true, length = 255)
    private TransactionUniqueIdentifier uniqueIdentifier;

    @Column(name = "description")
    private @Nullable String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    private Transaction(
            TransactionAmountRials amountRials,
            String username,
            TransactionUniqueIdentifier uniqueIdentifier,
            @Nullable String description) {
        this.amountRials = amountRials;
        this.username = username;
        this.uniqueIdentifier = uniqueIdentifier;
        this.description = description;
    }

    public static Transaction newTransaction(
            TransactionAmountRials amountRials,
            String username,
            TransactionUniqueIdentifier uniqueIdentifier,
            @Nullable String description) {
        return new Transaction(amountRials, username, uniqueIdentifier, description);
    }
}
