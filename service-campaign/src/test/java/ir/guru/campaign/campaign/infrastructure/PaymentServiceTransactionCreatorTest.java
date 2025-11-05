package ir.guru.campaign.campaign.infrastructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import ir.guru.campaign.campaign.DonationAmountRials;
import ir.guru.campaign.campaign.DonationUniqueIdentifier;
import ir.guru.campaign.campaign.DonationXerox;
import ir.guru.campaign.campaign.TransactionCreator;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTransactionCreatorTest {

    @Mock
    private PaymentServiceClient paymentServiceClient;

    private PaymentServiceTransactionCreator transactionCreator;

    @BeforeEach
    void setUp() throws Exception {
        Constructor<PaymentServiceTransactionCreator> constructor =
                PaymentServiceTransactionCreator.class.getDeclaredConstructor(PaymentServiceClient.class);
        constructor.setAccessible(true);
        transactionCreator = constructor.newInstance(paymentServiceClient);
    }

    @Test
    void createTransactionDelegatesToPaymentService() throws Exception {
        DonationXerox donation =
                new DonationXerox(10L, "john", DonationAmountRials.of(1_500L), new DonationUniqueIdentifier("uid-1"));

        transactionCreator.createTransaction(donation);

        PaymentServiceClient.CreateTransactionRequest expectedRequest =
                new PaymentServiceClient.CreateTransactionRequest(
                        "john", "Donation '10' transaction", donation.amountRials(), donation.uniqueIdentifier());
        verify(paymentServiceClient).createTransaction(expectedRequest);
    }

    @Test
    void createTransactionTranslatesConflictToDuplicateTransaction() {
        DonationXerox donation =
                new DonationXerox(11L, "kate", DonationAmountRials.of(2_000L), new DonationUniqueIdentifier("uid-2"));

        doThrow(new TestConflictException())
                .when(paymentServiceClient)
                .createTransaction(any(PaymentServiceClient.CreateTransactionRequest.class));

        assertThatThrownBy(() -> transactionCreator.createTransaction(donation))
                .isInstanceOf(TransactionCreator.TransactionCreationException.DuplicateTransactionException.class)
                .extracting("donation")
                .isEqualTo(donation);
    }

    @Test
    void createTransactionTranslatesOtherFeignExceptionsToUnknown() {
        DonationXerox donation =
                new DonationXerox(12L, "mike", DonationAmountRials.of(2_000L), new DonationUniqueIdentifier("uid-3"));

        doThrow(new TestFeignException(500))
                .when(paymentServiceClient)
                .createTransaction(any(PaymentServiceClient.CreateTransactionRequest.class));

        assertThatThrownBy(() -> transactionCreator.createTransaction(donation))
                .isInstanceOf(TransactionCreator.TransactionCreationException.UnknownTransactionException.class)
                .extracting("donation")
                .isEqualTo(donation);
    }

    private static Request dummyRequest() {
        return Request.create(
                Request.HttpMethod.POST,
                "http://localhost",
                Map.of(),
                null,
                StandardCharsets.UTF_8,
                new RequestTemplate());
    }

    private static final class TestConflictException extends FeignException.Conflict {
        private TestConflictException() {
            super("Conflict", dummyRequest(), null, Map.of());
        }
    }

    private static final class TestFeignException extends FeignException {
        private TestFeignException(int status) {
            super(status, "error", dummyRequest(), null, Map.of());
        }
    }
}
