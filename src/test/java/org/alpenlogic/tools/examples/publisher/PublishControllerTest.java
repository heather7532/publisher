package org.alpenlogic.tools.examples.publisher;

import org.alpenlogic.tools.examples.controllers.PublishController;
import org.alpenlogic.tools.examples.events.StartStopEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class PublishControllerTest {
    private static final Logger log = LogManager.getLogger(PublishControllerTest.class);

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private PublishController publishController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        publishController = new PublishController(eventPublisher);
    }

    @Test
    public void testStartPublishing() {
        String msg = "Test message";
        int max = 5;

        publishController.startPublishing(msg, max);

        ArgumentCaptor<StartStopEvent> eventCaptor = ArgumentCaptor.forClass(StartStopEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        StartStopEvent event = eventCaptor.getValue();
        assertEquals(msg, event.getMsg());
        assertEquals(max, event.getMax());
        assertFalse(event.isStop());
    }

    @Test
    public void testStopPublishing() {
        publishController.stopPublishing();

        ArgumentCaptor<StartStopEvent> eventCaptor = ArgumentCaptor.forClass(StartStopEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        StartStopEvent event = eventCaptor.getValue();
        assertNull(event.getMsg());
        assertEquals(0, event.getMax());
        assertTrue(event.isStop());
    }
}