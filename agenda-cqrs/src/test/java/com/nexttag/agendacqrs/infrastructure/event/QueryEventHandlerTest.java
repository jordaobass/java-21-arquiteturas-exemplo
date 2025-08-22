package com.nexttag.agendacqrs.infrastructure.event;

import com.nexttag.agendacqrs.domain.event.EventCreated;
import com.nexttag.agendacqrs.domain.event.EventDeleted;
import com.nexttag.agendacqrs.domain.event.EventUpdated;
import com.nexttag.agendacqrs.infrastructure.repository.QueryRepository;
import com.nexttag.agendacqrs.query.model.EventQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class QueryEventHandlerTest {

    @Mock
    private QueryRepository repository;

    private QueryEventHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new QueryEventHandler(repository);
    }

    @Test
    void onEventCreated() {
        UUID id = UUID.randomUUID();
        EventCreated event = new EventCreated(id, "Title", "Desc", LocalDateTime.now());

        handler.onEventCreated(event);

        verify(repository).add(any(EventQuery.class));
    }

    @Test
    void onEventUpdated() {
        UUID id = UUID.randomUUID();
        EventUpdated event = new EventUpdated(id, "New Title", "New Desc", LocalDateTime.now().plusDays(1));

        handler.onEventUpdated(event);

        verify(repository).update(any(EventQuery.class));
    }

    @Test
    void onEventDeleted() {
        UUID id = UUID.randomUUID();
        EventDeleted event = new EventDeleted(id);

        handler.onEventDeleted(event);

        verify(repository).remove(id);
    }
}