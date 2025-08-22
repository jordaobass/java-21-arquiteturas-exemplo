package com.nexttag.agendaclean.framework.adapter.persistence;

import com.nexttag.agendaclean.entity.Event;
import com.nexttag.agendaclean.interfaceadapter.gateway.EventRepositoryGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EventRepositoryImpl implements EventRepositoryGateway {
    private final EventJpaRepository jpaRepository;

    public EventRepositoryImpl(EventJpaRepository jpaRepository) {
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