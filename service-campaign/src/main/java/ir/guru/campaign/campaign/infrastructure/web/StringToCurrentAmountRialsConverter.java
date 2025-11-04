package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.CurrentAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
final class StringToCurrentAmountRialsConverter implements Converter<String, CurrentAmountRials> {
    @Override
    public CurrentAmountRials convert(String source) {
        return CurrentAmountRials.of(Long.parseLong(source));
    }
}
