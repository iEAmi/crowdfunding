package ir.guru.campaign.campaign.infrastructure.validation;

import ir.guru.campaign.campaign.DonationAmountRials;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.Nullable;

/**
 * Null consider valid.
 */
public class DonationAmountRialsPositiveValidator implements ConstraintValidator<Positive, DonationAmountRials> {

    public DonationAmountRialsPositiveValidator() {}

    @Override
    public boolean isValid(
            @Nullable DonationAmountRials donationAmountRials, ConstraintValidatorContext constraintValidatorContext) {
        // Null is valid
        if (donationAmountRials == null) return true;

        return donationAmountRials.value() > 0;
    }
}
