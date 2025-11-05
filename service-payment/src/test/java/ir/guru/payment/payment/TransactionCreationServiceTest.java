package ir.guru.payment.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionCreationServiceTest {

    @Mock
    private TransactionFactory transactionFactory;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionImporter importer;

    private TransactionCreationService service;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<TransactionCreationService> constructor = TransactionCreationService.class.getDeclaredConstructor(
                TransactionFactory.class, TransactionRepository.class);
        constructor.setAccessible(true);
        service = constructor.newInstance(transactionFactory, transactionRepository);
    }

    @Test
    void createTransactionDelegatesToFactoryAndRepository() throws Exception {
        Transaction transaction = Transaction.newTransaction(
                new TransactionAmountRials(5_500L), "user", new TransactionUniqueIdentifier("unique"), "desc");

        when(transactionFactory.createTransaction(importer)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = service.createTransaction(importer);

        assertThat(result).isEqualTo(transaction);
        verify(transactionFactory).createTransaction(importer);
        verify(transactionRepository).save(transaction);
    }
}
