package ir.guru.payment.payment.infrastructure.web;

import ir.guru.payment.payment.TransactionUniqueIdentifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
final class StringToTransactionUniqueIdentifierConverter implements Converter<String, TransactionUniqueIdentifier> {
    @Override
    public TransactionUniqueIdentifier convert(String source) {
        return new TransactionUniqueIdentifier(source);
    }
}
