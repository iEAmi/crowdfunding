package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TransactionFacadeTest {

    @Mock
    private GetTransactionService getTransactionService;

    @Mock
    private TransactionCreationService transactionCreationService;

    @Mock
    private Pageable pageable;

    @Mock
    private TransactionImporter importer;

    private TransactionFacade transactionFacade;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<TransactionFacade> constructor = TransactionFacade.class.getDeclaredConstructor(
                GetTransactionService.class, TransactionCreationService.class);
        constructor.setAccessible(true);
        transactionFacade = constructor.newInstance(getTransactionService, transactionCreationService);
    }

    @Test
    void findByIdDelegatesToGetTransactionService() {
        TransactionXerox xerox = mock(TransactionXerox.class);
        when(getTransactionService.findById(10L)).thenReturn(Optional.of(xerox));

        Optional<TransactionXerox> result = transactionFacade.findById(10L);

        assertThat(result).containsSame(xerox);
        verify(getTransactionService).findById(10L);
    }

    @Test
    void findByUniqueIdentifierDelegatesToGetTransactionService() {
        TransactionUniqueIdentifier identifier = new TransactionUniqueIdentifier("abc");
        TransactionXerox xerox = mock(TransactionXerox.class);
        when(getTransactionService.findByUniqueIdentifier(identifier)).thenReturn(Optional.of(xerox));

        Optional<TransactionXerox> result = transactionFacade.findByUniqueIdentifier(identifier);

        assertThat(result).containsSame(xerox);
        verify(getTransactionService).findByUniqueIdentifier(identifier);
    }

    @Test
    void filterTransactionsDelegatesToGetTransactionService() {
        TransactionFilter filter = new TransactionFilter("alice", null, null);
        TransactionXerox xerox = mock(TransactionXerox.class);
        when(getTransactionService.filterTransactions(filter, pageable)).thenReturn(Set.of(xerox));

        Set<TransactionXerox> result = transactionFacade.filterTransactions(filter, pageable);

        assertThat(result).containsExactly(xerox);
        verify(getTransactionService).filterTransactions(filter, pageable);
    }

    @Test
    void createTransactionDelegatesToCreationServiceAndReturnsXerox() throws Exception {
        Transaction transaction = Transaction.newTransaction(
                new TransactionAmountRials(2_000L), "user", new TransactionUniqueIdentifier("id-1"), "desc");
        setField(transaction, Transaction.Fields.id, 7L);
        setField(transaction, Transaction.Fields.createdAt, LocalDateTime.parse("2024-03-10T09:15:00"));

        when(transactionCreationService.createTransaction(importer)).thenReturn(transaction);

        TransactionXerox result = transactionFacade.createTransaction(importer);

        TransactionXerox expected = TransactionXerox.of(transaction);
        assertThat(result).isEqualTo(expected);
        verify(transactionCreationService).createTransaction(importer);
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
