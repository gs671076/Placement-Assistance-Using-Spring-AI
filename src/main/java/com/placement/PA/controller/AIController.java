package com.placement.PA.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Controller to handle AI-related interactions using OllamaChatModel.
 */
@RestController
@RequestMapping("/student/ai")
public class AIController {
    
     private final ChatClient chatClient;
     private static final Logger logger=LoggerFactory.getLogger(AIController.class);
    public AIController() {
        this.chatClient=null;
    }

    @Autowired
    public AIController(ChatClient.Builder builder) {
        this.chatClient=builder.build();
    }

    
 @GetMapping("/generateStream")
	public Flux<String> generateStream(@RequestParam(value = "message") String message) {
            if(message==null||message.isEmpty())
            {
            logger.error("Message is null");
            
            }
            return this.chatClient.prompt().user(message).stream().content();
    }

   
}
