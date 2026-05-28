package about.chatbot.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        if (request == null || !StringUtils.hasText(request.message())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message is required");
        }

        ChatClient.ChatClientRequestSpec prompt = chatClient.prompt();
        if (StringUtils.hasText(request.systemPrompt())) {
            prompt = prompt.system(request.systemPrompt());
        }

        String answer = prompt.user(request.message()).call().content();

        return new ChatResponse(answer);
    }
}
