package ir.guru.campaign.campaign;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "application.donation")
record DonationProperties(@NotNull Duration donationWindow) {
}
