package org.alpenlogic.tools.examples.publisher;

import org.alpenlogic.tools.examples.controllers.PublishController;
import org.alpenlogic.tools.examples.events.PublishStartStopEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublishController.class)
public class PublishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PublishController publishController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartPublishing() throws Exception {
        mockMvc.perform(post("/publish/start")
                        .param("msg", "Test message")
                        .param("max", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<PublishStartStopEvent> eventCaptor = ArgumentCaptor.forClass(PublishStartStopEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        PublishStartStopEvent event = eventCaptor.getValue();
        assertEquals("Test message", event.getMsg());
        assertEquals(5, event.getMax());
        assertEquals(false, event.isStop());
    }

    @Test
    public void testStopPublishing() throws Exception {
        mockMvc.perform(post("/publish/stop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<PublishStartStopEvent> eventCaptor = ArgumentCaptor.forClass(PublishStartStopEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        PublishStartStopEvent event = eventCaptor.getValue();
        assertEquals(null, event.getMsg());
        assertEquals(0, event.getMax());
        assertEquals(true, event.isStop());
    }
}