package about.chatbot.chat;

import about.chatbot.config.JacksonConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatRequestTestController.class)
@Import(JacksonConfig.class)
class ChatRequestJsonTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void readsValidChatRequest() throws Exception {
        mockMvc.perform(post("/test/chat-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "message": "Hello!",
                                  "systemPrompt": "You are a helpful assistant."
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello!"))
                .andExpect(jsonPath("$.systemPrompt").value("You are a helpful assistant."));
    }

    @Test
    void rejectsDuplicateSystemPromptKeys() throws Exception {
        mockMvc.perform(post("/test/chat-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "message": "Hello!",
                                  "systemPrompt": "You are a helpful assistant.",
                                  "systemPrompt": ""
                                }
                """))
                .andExpect(status().isBadRequest());
    }
}
