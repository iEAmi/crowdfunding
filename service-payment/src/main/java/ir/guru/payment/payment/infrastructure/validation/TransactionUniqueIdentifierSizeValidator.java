package ir.guru.payment.payment.infrastructure.validation;

import ir.guru.payment.payment.TransactionUniqueIdentifier;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * Null consider valid.
 */
public class TransactionUniqueIdentifierSizeValidator implements ConstraintValidator<Size, TransactionUniqueIdentifier> {
    private int min;
    private int max;

    public TransactionUniqueIdentifierSizeValidator() {}

    @Override
    public void initialize(Size constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(@Nullable TransactionUniqueIdentifier uniqueIdentifier, ConstraintValidatorContext constraintValidatorContext) {
        // Null is valid
        if (uniqueIdentifier == null) return true;

        int length = uniqueIdentifier.toString().length();

        return length >= this.min && length <= this.max;
    }
}
