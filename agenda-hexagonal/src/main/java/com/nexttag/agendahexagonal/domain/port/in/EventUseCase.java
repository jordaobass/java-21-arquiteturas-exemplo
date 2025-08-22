package com.nexttag.agendahexagonal.domain.port.in;

import com.nexttag.agendahexagonal.domain.entity.Event;

import java.util.List;
import java.util.UUID;

public interface EventUseCase {
    Event create(Event event);
    List<Event> listAll();
    Event update(UUID id, Event event);
    void delete(UUID id);
}