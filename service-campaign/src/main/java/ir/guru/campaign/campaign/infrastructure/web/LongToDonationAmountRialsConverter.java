package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.DonationAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
final class LongToDonationAmountRialsConverter implements Converter<Long, DonationAmountRials> {
    @Override
    public DonationAmountRials convert(Long source) {
        return DonationAmountRials.of(source);
    }
}
