package ir.guru.payment.payment.infrastructure.web;

import ir.guru.payment.payment.TransactionAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
final class LongToTransactionAmountRialsConverter implements Converter<Long, TransactionAmountRials> {
    @Override
    public TransactionAmountRials convert(Long source) {
        return new TransactionAmountRials(source);
    }
}
