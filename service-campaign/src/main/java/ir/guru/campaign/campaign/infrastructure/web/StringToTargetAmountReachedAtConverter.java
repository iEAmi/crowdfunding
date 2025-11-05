package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.TargetAmountReachedAt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
final class StringToTargetAmountReachedAtConverter implements Converter<String, TargetAmountReachedAt> {
    @Override
    public TargetAmountReachedAt convert(String source) {
        return new TargetAmountReachedAt(source);
    }
}
