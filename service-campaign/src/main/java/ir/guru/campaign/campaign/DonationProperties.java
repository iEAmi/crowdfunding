package ir.guru.campaign.campaign;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.donation")
record DonationProperties(@NotNull Duration donationWindow) {}
