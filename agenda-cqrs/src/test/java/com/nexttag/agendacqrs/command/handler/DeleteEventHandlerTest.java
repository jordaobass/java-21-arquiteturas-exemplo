package com.nexttag.agendacqrs.command.handler;

import com.nexttag.agendacqrs.domain.event.EventDeleted;
import com.nexttag.agendacqrs.infrastructure.repository.CommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.mockito.Mockito.verify;

class DeleteEventHandlerTest {

    @Mock
    private CommandRepository repository;

    @Mock
    private ApplicationEventPublisher publisher;

    private DeleteEventHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new DeleteEventHandler(repository, publisher);
    }

    @Test
    void handle() {
        UUID id = UUID.randomUUID();

        handler.handle(id);

        verify(repository).delete(id);

        ArgumentCaptor<EventDeleted> captor = ArgumentCaptor.forClass(EventDeleted.class);
        verify(publisher).publishEvent(captor.capture());
        EventDeleted event = captor.getValue();
        assert id.equals(event.id());
    }
}