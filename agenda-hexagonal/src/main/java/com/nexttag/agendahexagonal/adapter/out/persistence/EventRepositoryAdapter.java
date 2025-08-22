package com.nexttag.agendahexagonal.adapter.out.persistence;

import com.nexttag.agendahexagonal.domain.entity.Event;
import com.nexttag.agendahexagonal.domain.port.out.EventRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EventRepositoryAdapter implements EventRepositoryPort {
    private final EventJpaRepository jpaRepository;

    public EventRepositoryAdapter(EventJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = toEntity(event);
        EventEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Event> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private EventEntity toEntity(Event event) {
        return new EventEntity(event.id(), event.title(), event.description(), event.date());
    }

    private Event toDomain(EventEntity entity) {
        return new Event(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDate());
    }
}