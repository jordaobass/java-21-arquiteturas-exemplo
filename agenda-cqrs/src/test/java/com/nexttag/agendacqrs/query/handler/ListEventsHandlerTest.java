package com.nexttag.agendacqrs.query.handler;

import com.nexttag.agendacqrs.infrastructure.repository.QueryRepository;
import com.nexttag.agendacqrs.query.model.EventQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ListEventsHandlerTest {

    @Mock
    private QueryRepository repository;

    private ListEventsHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ListEventsHandler(repository);
    }

    @Test
    void handle() {
        EventQuery event = new EventQuery(UUID.randomUUID(), "Title", "Desc", LocalDateTime.now());
        when(repository.findAll()).thenReturn(List.of(event));

        List<EventQuery> result = handler.handle();
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
    }
}