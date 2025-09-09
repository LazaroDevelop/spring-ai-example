package net.developer.space.chat_assistance_ai_rag.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up system prompts.
 * This class will define beans and configurations required for system prompts.
 * 
 * @author Lazaro Noel Guerra Medina
 * @since 2025-09-08
 */
@Configuration
@ConfigurationProperties(prefix = "application.system")
public class SystemPromptConfig {
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
