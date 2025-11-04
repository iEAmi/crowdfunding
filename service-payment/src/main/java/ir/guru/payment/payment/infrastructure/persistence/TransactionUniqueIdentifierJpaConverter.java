package ir.guru.payment.payment.infrastructure.persistence;

import ir.guru.payment.payment.TransactionUniqueIdentifier;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
final class TransactionUniqueIdentifierJpaConverter implements AttributeConverter<TransactionUniqueIdentifier, String> {

    @Override
    public String convertToDatabaseColumn(TransactionUniqueIdentifier attribute) {
        return attribute.toString();
    }

    @Override
    public TransactionUniqueIdentifier convertToEntityAttribute(String dbData) {
        return new TransactionUniqueIdentifier(dbData);
    }
}
