package org.alpenlogic.tools.examples.publisher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Getter
@Setter
public class KafkaProperties {
    private Producer producer;

    @Getter
    @Setter
    public static class Producer {
        private String bootstrapServers;
        private String topic;
        private String keySerializer;
        private String valueSerializer;
        private String acks;
        private int retries;
        private Properties properties;
        private Ssl ssl;

        @Getter
        @Setter
        public static class Properties {
            private int retryBackoffMs;
            private String schemaRegistryUrl;
        }

        @Getter
        @Setter
        public static class Ssl {
            private boolean enabled;
        }
    }
}