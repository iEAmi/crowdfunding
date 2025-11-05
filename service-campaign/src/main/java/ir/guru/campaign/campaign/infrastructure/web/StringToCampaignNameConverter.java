package ir.guru.campaign.campaign.infrastructure.web;

import ir.guru.campaign.campaign.CampaignName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
final class StringToCampaignNameConverter implements Converter<String, CampaignName> {
    @Override
    public CampaignName convert(String source) {
        return new CampaignName(source);
    }
}
