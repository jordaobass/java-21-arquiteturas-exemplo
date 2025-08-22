package com.nexttag.agendaclean.interfaceadapter.gateway;

import com.nexttag.agendaclean.entity.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepositoryGateway {
    Event save(Event event);
    List<Event> findAll();
    Optional<Event> findById(UUID id);
    void deleteById(UUID id);
}