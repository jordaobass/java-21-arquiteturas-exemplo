package com.nexttag.agendalayered.service;

import com.nexttag.agendalayered.model.Event;
import com.nexttag.agendalayered.repository.EventRepository;
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

/**
 * Testes unitários para o EventService.
 *
 * Verifica a lógica de negócio isoladamente, usando mocks para o repositório.
 *
 * Responsabilidades:
 * - Testar cenários de criação, listagem, atualização e deleção.
 * - Verificar exceções para casos como "não encontrado".
 */
class EventServiceTest {

    @Mock
    private EventRepository repository;

    private EventService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new EventService(repository);
    }

    @Test
    void create() {
        Event event = new Event("Title", "Desc", LocalDateTime.now());
        when(repository.save(any(Event.class))).thenReturn(event);

        Event result = service.create(event);
        assertEquals(event.getTitle(), result.getTitle());
        verify(repository).save(event);
    }

    @Test
    void listAll() {
        Event event = new Event("Title", "Desc", LocalDateTime.now());
        when(repository.findAll()).thenReturn(Collections.singletonList(event));

        assertEquals(1, service.listAll().size());
        verify(repository).findAll();
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        Event existing = new Event("Old", "Old Desc", LocalDateTime.now());
        existing.setId(id);
        Event updateData = new Event("New", "New Desc", LocalDateTime.now().plusDays(1));
        Event updated = new Event("New", "New Desc", LocalDateTime.now().plusDays(1));
        updated.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Event.class))).thenReturn(updated);

        Event result = service.update(id, updateData);
        assertEquals("New", result.getTitle());
        verify(repository).save(any(Event.class));
    }

    @Test
    void updateNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update(id, new Event()));
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);
        verify(repository).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.delete(id));
    }
}