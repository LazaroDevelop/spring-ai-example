package net.developer.space.chat_assistance_ai_rag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ChatAssistanceAiRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatAssistanceAiRagApplication.class, args);
	}

}
