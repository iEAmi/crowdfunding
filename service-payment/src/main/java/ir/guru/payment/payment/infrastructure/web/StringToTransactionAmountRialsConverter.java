package ir.guru.payment.payment.infrastructure.web;

import ir.guru.payment.payment.TransactionAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
final class StringToTransactionAmountRialsConverter implements Converter<String, TransactionAmountRials> {
    @Override
    public TransactionAmountRials convert(String source) {
        return new TransactionAmountRials(Long.parseLong(source));
    }
}
