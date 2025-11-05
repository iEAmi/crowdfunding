package ir.guru.campaign.campaign.infrastructure.validation;

import ir.guru.campaign.campaign.TargetAmountRials;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Positive;
import java.lang.annotation.*;
import org.springframework.lang.Nullable;

/**
 * Null consider valid.
 */
public class TargetAmountRialsPositiveValidator implements ConstraintValidator<Positive, TargetAmountRials> {

    public TargetAmountRialsPositiveValidator() {}

    @Override
    public boolean isValid(
            @Nullable TargetAmountRials targetAmountRials, ConstraintValidatorContext constraintValidatorContext) {
        // Null is valid
        if (targetAmountRials == null) return true;

        return targetAmountRials.value() > 0;
    }
}
