package org.alpenlogic.tools.examples.publisher;

import org.alpenlogic.tools.examples.avro.Message;
import org.alpenlogic.tools.examples.events.StartStopEvent;
import org.alpenlogic.tools.examples.publisher.config.LocalKafkaProperties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class MessagePublisher {
    private static final Logger log = LogManager.getLogger(MessagePublisher.class);

    private boolean publishing = false;
    private int counter = 0;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private LocalKafkaProperties localKafkaProperties;

    @EventListener
    public void handlePublishEvent(StartStopEvent event) {
        log.info("Received event: {}", event);
        if (event.isStop()) {
            stopPublishing();
        } else {
            startPublishing(event.getMsg(), event.getMax());
        }
    }

    private void startPublishing(String msg, int max) {
        publishing = true;
        counter = 0;
        new Thread(() -> {
            while (publishing && counter < max) {
                log.info(msg + " " + counter);
                sendToKafka(msg + " " + counter);
                counter++;
                try {
                    Thread.sleep(500); // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            publishing = false;
        }).start();
    }

    private void stopPublishing() {
        publishing = false;
    }

    private void sendToKafka(String messageContent) {
        String topic = localKafkaProperties.getTopic();
        Message message = Message.newBuilder()
                .setId(UUID.randomUUID())
                .setContentType("text/plain")
                .setContent(messageContent)
                .setTimeSent(Instant.now())
                .build();
        kafkaTemplate.send(new ProducerRecord<>(topic, message));
        log.info("Sent message to Kafka topic {}: {}", topic, message);
    }
}