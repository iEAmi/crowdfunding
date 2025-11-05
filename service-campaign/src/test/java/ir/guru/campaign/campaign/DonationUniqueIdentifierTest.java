package ir.guru.campaign.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DonationUniqueIdentifierTest {

    @Test
    void constructorRejectsBlankValue() {
        assertThatThrownBy(() -> new DonationUniqueIdentifier("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DonationUniqueIdentifier("   ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void randomGeneratesNonBlankIdentifier() {
        DonationUniqueIdentifier first = DonationUniqueIdentifier.random();
        DonationUniqueIdentifier second = DonationUniqueIdentifier.random();

        assertThat(first.toString()).isNotBlank();
        assertThat(second.toString()).isNotBlank();
        assertThat(first.toString()).isNotEqualTo(second.toString());
    }

    @Test
    void trimsValueAndUsesItForStringRepresentation() {
        DonationUniqueIdentifier identifier = new DonationUniqueIdentifier("  id-1  ");

        assertThat(identifier.toString()).isEqualTo("id-1");
    }
}
