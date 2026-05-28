package about.chatbot.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.StreamReadFeature;

@Configuration
public class JacksonConfig {

    @Bean
    JsonMapperBuilderCustomizer strictDuplicateJsonKeys() {
        return builder -> builder.enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION);
    }
}
