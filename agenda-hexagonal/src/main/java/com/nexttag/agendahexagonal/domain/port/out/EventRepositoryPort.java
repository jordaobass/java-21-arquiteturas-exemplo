package com.nexttag.agendahexagonal.domain.port.out;

import com.nexttag.agendahexagonal.domain.entity.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepositoryPort {
    Event save(Event event);
    List<Event> findAll();
    Optional<Event> findById(UUID id);
    void deleteById(UUID id);
}