package ir.guru.campaign.campaign.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import ir.guru.campaign.campaign.CampaignName;
import org.junit.jupiter.api.Test;

class CampaignNameJpaConverterTest {

    private final CampaignNameJpaConverter converter = new CampaignNameJpaConverter();

    @Test
    void convertsEntityAttributeToDatabaseColumn() {
        CampaignName name = new CampaignName("Relief");

        assertThat(converter.convertToDatabaseColumn(name)).isEqualTo("Relief");
    }

    @Test
    void convertsDatabaseColumnToEntityAttribute() {
        CampaignName name = converter.convertToEntityAttribute("Hope");

        assertThat(name.toString()).isEqualTo("Hope");
    }

    @Test
    void handlesNullValuesSafely() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
