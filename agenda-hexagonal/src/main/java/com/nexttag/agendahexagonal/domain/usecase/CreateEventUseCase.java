package com.nexttag.agendahexagonal.domain.usecase;


import com.nexttag.agendahexagonal.domain.entity.Event;
import com.nexttag.agendahexagonal.domain.port.out.EventRepositoryPort;

import java.time.LocalDateTime;

public class CreateEventUseCase {
    private final EventRepositoryPort repository;

    public CreateEventUseCase(EventRepositoryPort repository) {
        this.repository = repository;
    }

    public Event execute(String title, String description, LocalDateTime date) {
        Event event = new Event(title, description, date);
        return repository.save(event);
    }
}