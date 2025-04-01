package com.example.myproject.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIConsultService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String MODEL = "gpt-3.5-turbo";

    public String consult(String query) {
        OpenAiService service = new OpenAiService(apiKey);
        
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), query));

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(MODEL)
                .messages(messages)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        try {
            return service.createChatCompletion(request)
                    .getChoices().get(0)
                    .getMessage().getContent();
        } catch (Exception e) {
            return "AI 服务出现错误: " + e.getMessage();
        }
    }
}