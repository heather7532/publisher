package org.alpenlogic.tools.examples.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Message {
    private UUID id;
    private String contentType;
    private String content;
    private long timeSent;

    private final ObjectMapper objectMapper;

    public Message(UUID id, String contentType, String content,
                   long timeSent, ObjectMapper objectMapper) {
        this.id = id;
        this.contentType = contentType;
        this.content = content;
        this.timeSent = timeSent;
        this.objectMapper = objectMapper;
    }

    /**
     * Validates if the content field contains valid JSON.
     *
     * @return true if content is valid JSON, false otherwise.
     */
    public boolean isValidJson() {
        try {
            objectMapper.readTree(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}