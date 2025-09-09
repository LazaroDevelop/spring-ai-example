package net.developer.space.chat_assistance_ai_rag.config;

import org.springframework.stereotype.Component;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Configuration class for setting up tools.
 * This class will define beans and configurations required for tools.
 * 
 * @author Lazaro Noel Guerra Medina
 * @since 2025-09-08
 */


@Component
public class ToolConfig {

    @Tool(description = "Estimate the health of an animal based on species and weight, and suggest the next zoo visit.")
    public AnimalHealthResponse estimateAnimalHealth(
            @ToolParam(description = "The unique ID of the animal") int id,
            @ToolParam(description = "The species name of the animal") String species,
            @ToolParam(description = "The weight of the animal in kilograms") BigDecimal weight
    ) {
        // Fake thresholds, normally you'd query a database or knowledge base
        BigDecimal healthyWeight = switch (species.toLowerCase()) {
            case "lion" -> BigDecimal.valueOf(190f); // average male lion
            case "elephant" -> BigDecimal.valueOf(4000f);
            case "zebra" -> BigDecimal.valueOf(350f);
            default -> BigDecimal.valueOf(100f); // fallback
        };

        String healthStatus;
        if (weight.compareTo(healthyWeight.multiply(BigDecimal.valueOf(0.8))) < 0) {
            healthStatus = "underweight";
        } else if (weight.compareTo( healthyWeight.multiply(BigDecimal.valueOf(1.2))) > 0) {
            healthStatus = "overweight";
        } else {
            healthStatus = "healthy";
        }

        Instant nextVisit = switch (healthStatus) {
            case "underweight", "overweight" -> Instant.now().plus(1, ChronoUnit.DAYS);
            default -> Instant.now().plus(7, ChronoUnit.DAYS);
        };

        return new AnimalHealthResponse(id, species, weight, healthStatus, nextVisit.toString());
    }

    public record AnimalHealthResponse(
            int id,
            String species,
            BigDecimal weight,
            String healthStatus,
            String suggestedNextVisit
    ) {}
}
