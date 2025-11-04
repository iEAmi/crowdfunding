package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.CurrentAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
final class LongToCurrentAmountRialsConverter implements Converter<Long, CurrentAmountRials> {
    @Override
    public CurrentAmountRials convert(Long source) {
        return CurrentAmountRials.of(source);
    }
}
