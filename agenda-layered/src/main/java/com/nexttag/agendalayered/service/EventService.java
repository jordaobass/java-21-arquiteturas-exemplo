package com.nexttag.agendalayered.service;

import com.nexttag.agendalayered.model.Event;
import com.nexttag.agendalayered.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Serviço para lógica de negócio relacionada a Eventos.
 *
 * Na camada de negócio, esta classe orquestra operações, chamando o repositório.
 * Aqui fica a lógica principal, como verificações e transformações.
 *
 * Responsabilidades:
 * - Implementar CRUD com verificações básicas (ex.: existência para update/delete).
 */
@Service
public class EventService {
    private final EventRepository repository;

    /**
     * Construtor com injeção do repositório.
     *
     * @param repository Repositório de dados.
     */
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria um novo evento.
     *
     * @param event Evento a criar.
     * @return Evento salvo.
     */
    public Event create(Event event) {
        return repository.save(event);
    }

    /**
     * Lista todos os eventos.
     *
     * @return Lista de eventos.
     */
    public List<Event> listAll() {
        return repository.findAll();
    }

    /**
     * Atualiza um evento existente.
     *
     * @param id ID do evento.
     * @param event Dados atualizados.
     * @return Evento atualizado.
     */
    public Event update(UUID id, Event event) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitle(event.getTitle());
                    existing.setDescription(event.getDescription());
                    existing.setDate(event.getDate());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    /**
     * Deleta um evento.
     *
     * @param id ID do evento.
     */
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        repository.deleteById(id);
    }
}