package com.nexttag.agendahexagonal.domain.usecase;

import com.nexttag.agendahexagonal.domain.entity.Event;
import com.nexttag.agendahexagonal.domain.port.out.EventRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventUseCaseImplTest {

    @Mock
    private EventRepositoryPort repositoryPort;

    private EventUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new EventUseCaseImpl(repositoryPort);
    }

    @Test
    void create() {
        Event event = new Event("Title", "Desc", LocalDateTime.now());
        when(repositoryPort.save(any(Event.class))).thenReturn(event);

        Event result = useCase.create(event);
        assertEquals(event, result);
        verify(repositoryPort).save(event);
    }

    @Test
    void listAll() {
        Event event = new Event("Title", "Desc", LocalDateTime.now());
        when(repositoryPort.findAll()).thenReturn(Collections.singletonList(event));

        assertEquals(1, useCase.listAll().size());
        verify(repositoryPort).findAll();
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        Event existing = new Event(id, "Old", "Old Desc", LocalDateTime.now());
        Event updateData = new Event("New", "New Desc", LocalDateTime.now().plusDays(1));
        Event updated = new Event(id, "New", "New Desc", LocalDateTime.now().plusDays(1));

        when(repositoryPort.findById(id)).thenReturn(Optional.of(existing));
        when(repositoryPort.save(any(Event.class))).thenReturn(updated);

        Event result = useCase.update(id, updateData);
        assertEquals("New", result.title());
        verify(repositoryPort).save(any(Event.class));
    }

    @Test
    void updateNotFound() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> useCase.update(id, new Event("Title", "Desc", LocalDateTime.now())));
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(new Event("Title", "Desc", LocalDateTime.now())));

        useCase.delete(id);
        verify(repositoryPort).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> useCase.delete(id));
    }
}