package com.nexttag.agendacqrs.command.handler;

import com.nexttag.agendacqrs.command.model.EventCommand;
import com.nexttag.agendacqrs.domain.event.EventCreated;
import com.nexttag.agendacqrs.infrastructure.repository.CommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

class CreateEventHandlerTest {

    @Mock
    private CommandRepository repository;

    @Mock
    private ApplicationEventPublisher publisher;

    private CreateEventHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreateEventHandler(repository, publisher);
    }

    @Test
    void handle() {
        EventCommand command = new EventCommand("Title", "Desc", LocalDateTime.now());

        assertNotNull(handler.handle(command));

        verify(repository).save(command);

        ArgumentCaptor<EventCreated> captor = ArgumentCaptor.forClass(EventCreated.class);
        verify(publisher).publishEvent(captor.capture());
        EventCreated event = captor.getValue();
        assertNotNull(event.id());
    }
}