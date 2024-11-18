package org.alpenlogic.tools.examples.publisher;

import lombok.extern.slf4j.Slf4j;
import org.alpenlogic.tools.examples.events.PublishStartStopEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagePublisher {
    private boolean publishing = false;
    private int counter = 0;

    @EventListener
    public void handlePublishEvent(PublishStartStopEvent event) {
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
                System.out.println(msg + " " + counter);
                counter++;
                try {
                    Thread.sleep(1000); // Simulate delay
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
}