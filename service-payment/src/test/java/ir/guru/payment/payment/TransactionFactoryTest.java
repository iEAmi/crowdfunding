package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionFactoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionFactory factory;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<TransactionFactory> constructor =
                TransactionFactory.class.getDeclaredConstructor(TransactionRepository.class);
        constructor.setAccessible(true);
        factory = constructor.newInstance(transactionRepository);
    }

    @Test
    void createTransactionBuildsNewEntityWhenUniqueIdentifierNotUsed() throws Exception {
        TransactionAmountRials amount = new TransactionAmountRials(7_000L);
        TransactionUniqueIdentifier uniqueIdentifier = new TransactionUniqueIdentifier("uniq-1");
        TransactionImporter importer = new SimpleImporter("jane", "Payment", amount, uniqueIdentifier);

        when(transactionRepository.existsByUniqueIdentifier(uniqueIdentifier)).thenReturn(false);

        Transaction transaction = factory.createTransaction(importer);

        assertThat(transaction.getAmountRials()).isEqualTo(amount);
        assertThat(transaction.getUsername()).isEqualTo("jane");
        assertThat(transaction.getUniqueIdentifier()).isEqualTo(uniqueIdentifier);
        assertThat(transaction.getDescription()).isEqualTo("Payment");
    }

    @Test
    void createTransactionThrowsForDuplicateUniqueIdentifier() throws Exception {
        TransactionUniqueIdentifier uniqueIdentifier = new TransactionUniqueIdentifier("dup-2");
        TransactionImporter importer =
                new SimpleImporter("john", "Desc", new TransactionAmountRials(1_000L), uniqueIdentifier);

        when(transactionRepository.existsByUniqueIdentifier(uniqueIdentifier)).thenReturn(true);

        assertThatThrownBy(() -> factory.createTransaction(importer))
                .isInstanceOf(TransactionCreationException.DuplicateTransactionException.class);
    }

    private record SimpleImporter(
            String username,
            String description,
            TransactionAmountRials amountRials,
            TransactionUniqueIdentifier uniqueIdentifier)
            implements TransactionImporter {}
}
