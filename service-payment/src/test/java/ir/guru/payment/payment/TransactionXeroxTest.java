package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TransactionXeroxTest {

    @Test
    void ofCopiesRelevantFieldsFromEntity() throws Exception {
        TransactionAmountRials amount = new TransactionAmountRials(10_000L);
        TransactionUniqueIdentifier uniqueIdentifier = new TransactionUniqueIdentifier("ref-456");
        String username = "alice";
        String description = "Donation";

        Transaction entity = Transaction.newTransaction(amount, username, uniqueIdentifier, description);

        setField(entity, Transaction.Fields.id, 42L);
        setField(entity, Transaction.Fields.createdAt, LocalDateTime.parse("2024-02-10T12:00:00"));

        TransactionXerox xerox = TransactionXerox.of(entity);

        assertThat(xerox.id()).isEqualTo(42L);
        assertThat(xerox.amountRials()).isEqualTo(amount);
        assertThat(xerox.username()).isEqualTo(username);
        assertThat(xerox.uniqueIdentifier()).isEqualTo(uniqueIdentifier);
        assertThat(xerox.description()).isEqualTo(description);
        assertThat(xerox.createdAt()).isEqualTo(LocalDateTime.parse("2024-02-10T12:00:00"));
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
