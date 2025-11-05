package ir.guru.campaign.campaign.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.CampaignName;
import org.junit.jupiter.api.Test;

class StringToCampaignNameConverterTest {

    private final StringToCampaignNameConverter converter = new StringToCampaignNameConverter();

    @Test
    void convertsStringToCampaignName() {
        CampaignName name = converter.convert("  Relief  ");

        assertThat(name).isNotNull();
        assertThat(name.toString()).isEqualTo("Relief");
    }
}
