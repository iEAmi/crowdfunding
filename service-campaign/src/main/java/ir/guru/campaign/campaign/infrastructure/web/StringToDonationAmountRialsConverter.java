package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.DonationAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
final class StringToDonationAmountRialsConverter implements Converter<String, DonationAmountRials> {
    @Override
    public DonationAmountRials convert(String source) {
        return DonationAmountRials.of(Long.parseLong(source));
    }
}
