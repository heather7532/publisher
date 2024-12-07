package org.alpenlogic.tools.examples.publisher;

import org.alpenlogic.tools.examples.controllers.PublishController;
import org.alpenlogic.tools.examples.events.StartStopEvent;
import org.alpenlogic.tools.examples.publisher.config.KafkaProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    private static final Logger log = LogManager.getLogger(MessagePublisher.class);

    private boolean publishing = false;
    private int counter = 0;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaProperties kafkaProperties;

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

    private void sendToKafka(String message) {
        String topic = kafkaProperties.getTopic();
        String key = "key";
//        ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(topic, key, avroRecord);
        kafkaTemplate.send(topic, message);
        log.info("Sent message to Kafka topic {}: {}", topic, message);
    }
}
