package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.TargetAmountRials;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
final class LongToTargetAmountRialsConverter implements Converter<Long, TargetAmountRials> {
    @Override
    public TargetAmountRials convert(Long source) {
        return TargetAmountRials.of(source);
    }
}
