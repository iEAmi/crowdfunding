package ir.guru.payment.payment.infrastructure.validation;

import ir.guru.payment.payment.TransactionAmountRials;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.Nullable;

/**
 * Null consider valid.
 */
public class TransactionAmountRialsPositiveValidator implements ConstraintValidator<Positive, TransactionAmountRials> {

    public TransactionAmountRialsPositiveValidator() {}

    @Override
    public boolean isValid(
            @Nullable TransactionAmountRials amountRials, ConstraintValidatorContext constraintValidatorContext) {
        // Null is valid
        if (amountRials == null) return true;

        return amountRials.value() > 0;
    }
}
