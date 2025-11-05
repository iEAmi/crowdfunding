package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CampaignNameTest {

    @Test
    void constructorRejectsBlankNames() {
        assertThatThrownBy(() -> new CampaignName("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CampaignName("   ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void trimsValueAndUsesItForStringRepresentation() {
        CampaignName name = new CampaignName("  Save Animals ");

        assertThat(name.toString()).isEqualTo("Save Animals");
    }
}
