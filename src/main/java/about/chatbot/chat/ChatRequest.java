package about.chatbot.chat;

public record ChatRequest(
        String message,
        String systemPrompt
) {}
