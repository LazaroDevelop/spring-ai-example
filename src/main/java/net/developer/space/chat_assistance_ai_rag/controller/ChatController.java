package net.developer.space.chat_assistance_ai_rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller for handling chat requests and responses.
 * This class will manage endpoints for sending messages and receiving AI-generated replies.
 * 
 * @author Lazaro Noel Guerra Medina
 * @since 2025-09-08
 */

@Controller
@ResponseBody
public class ChatController {

    private final ChatClient ai;

    public ChatController(ChatClient ai) {
        this.ai = ai;
    }

    /**
     * User questin entrypoint
     */
    @GetMapping("/{user}/assitant")
    public String inquire(@PathVariable String user, @RequestParam String question) {        

        return ai
                .prompt()
                .user(question)
                .advisors(ai -> ai.param(ChatMemory.CONVERSATION_ID, user))
                .call()
                .content();
    }
    

}
