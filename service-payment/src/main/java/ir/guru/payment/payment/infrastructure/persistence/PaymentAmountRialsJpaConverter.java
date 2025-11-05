package ir.guru.payment.payment.infrastructure.persistence;

import ir.guru.payment.payment.TransactionAmountRials;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
final class PaymentAmountRialsJpaConverter implements AttributeConverter<TransactionAmountRials, Long> {

    @Override
    public Long convertToDatabaseColumn(TransactionAmountRials attribute) {
        return attribute.value();
    }

    @Override
    public TransactionAmountRials convertToEntityAttribute(Long dbData) {
        return new TransactionAmountRials(dbData);
    }
}
