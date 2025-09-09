package net.developer.space.chat_assistance_ai_rag.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.developer.space.chat_assistance_ai_rag.repository.AnimalsRepository;

/**
 * Configuration class for setting up the ChatClient with necessary properties.
 * This class will define beans and configurations required for the chat functionalities.
 * 
 * @author Lazaro Noel Guerra Medina
 * @since 2025-09-08
 */

@Configuration
public class ChatClientConfig {

    /**
     * Chat client bean
     * 
     * @param builder the builder of the client
     * @param advisor the advisor to provide memory for the chat context
     * @return an instance of the {@link ChatClient} class with the currents configurations
     */
    @Bean
    ChatClient chatClient(
        ChatClient.Builder builder, 
        PromptChatMemoryAdvisor advisor, 
        SystemPromptConfig promptConfig,
        VectorStore vectorStore,
        AnimalsRepository repository
        ) {

            repository.findAll().forEach(animal -> {
                var document = new Document("id: %s, name: %s, species: %s, habitat: %s, diet: %s, lifespan: %d, weight: %.2f, description: %s"
                    .formatted(
                        animal.id(),
                        animal.name(),
                        animal.species(),
                        animal.habitat(),
                        animal.diet(),
                        animal.lifespan(),
                        animal.weight(),
                        animal.description()
                    ));

                    vectorStore.add(List.of(document));
            });

        return builder
            .defaultSystem(promptConfig.getPrompt())
            .defaultAdvisors(advisor)
            .build();
    }

    /**
     * Prompt chat memory advisor configuration bean 
     * 
     * @param dataSource the datasource when the memory is allocated
     * @return an instance of {@link PrompChatMemoryAdvisor} with the configuration for the chat memory
     */
    @Bean
    PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource dataSource) {
        var jdbc = JdbcChatMemoryRepository
            .builder()
            .dataSource(dataSource)
            .build();

        var chatMemoryWindows = MessageWindowChatMemory
            .builder()
            .chatMemoryRepository(jdbc)
            .build();

        return PromptChatMemoryAdvisor.builder(chatMemoryWindows).build();
    }
}
