package com.nexttag.agendacqrs.command.handler;

import com.nexttag.agendacqrs.command.model.EventCommand;
import com.nexttag.agendacqrs.domain.event.EventUpdated;
import com.nexttag.agendacqrs.infrastructure.repository.CommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

class UpdateEventHandlerTest {

    @Mock
    private CommandRepository repository;

    @Mock
    private ApplicationEventPublisher publisher;

    private UpdateEventHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new UpdateEventHandler(repository, publisher);
    }

    @Test
    void handle() {
        UUID id = UUID.randomUUID();
        EventCommand command = new EventCommand("New Title", "New Desc", LocalDateTime.now().plusDays(1));

        handler.handle(id, command);

        verify(repository).update(id, command);

        ArgumentCaptor<EventUpdated> captor = ArgumentCaptor.forClass(EventUpdated.class);
        verify(publisher).publishEvent(captor.capture());
        EventUpdated event = captor.getValue();
        assert id.equals(event.id());
        assert "New Title".equals(event.title());
    }
}