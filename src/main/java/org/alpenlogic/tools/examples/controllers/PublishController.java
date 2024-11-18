package org.alpenlogic.tools.examples.controllers;

import lombok.extern.slf4j.Slf4j;
import org.alpenlogic.tools.examples.events.PublishStartStopEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/publish")
@Slf4j
public class PublishController {

    private final ApplicationEventPublisher eventPublisher;

    public PublishController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startPublishing(
            @RequestParam(name = "msg", required = true) String msg,
            @RequestParam(name = "max", required = false, defaultValue = "1") Integer max) {
        log.info("/publish/start called with msg: {} and max: {}", msg, max);
        int maxToSend = (max != null) ? max : 1;
        eventPublisher.publishEvent(new PublishStartStopEvent(msg, maxToSend, false));
        return new ResponseEntity<>("Publishing started", HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopPublishing() {
        log.info("/publish/stop called");
        eventPublisher.publishEvent(new PublishStartStopEvent(null, 0, true));
        return new ResponseEntity<>("Publishing stopped", HttpStatus.OK);
    }
}