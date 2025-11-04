package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.TargetAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: write test
@Component
final class StringToTargetAmountRialsConverter implements Converter<String, TargetAmountRials> {
    @Override
    public TargetAmountRials convert(String source) {
        return TargetAmountRials.of(Long.parseLong(source));
    }
}
