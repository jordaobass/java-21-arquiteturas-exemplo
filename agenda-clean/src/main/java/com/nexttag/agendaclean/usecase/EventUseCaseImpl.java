package com.nexttag.agendaclean.usecase;

import com.nexttag.agendaclean.entity.Event;
import com.nexttag.agendaclean.interfaceadapter.gateway.EventRepositoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventUseCaseImpl implements EventUseCase {
    private final EventRepositoryGateway gateway;

    public EventUseCaseImpl(EventRepositoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Event create(Event event) {
        return gateway.save(event);
    }

    @Override
    public List<Event> listAll() {
        return gateway.findAll();
    }

    @Override
    public Event update(UUID id, Event event) {
        return gateway.findById(id)
                .map(existing -> {
                    Event updated = existing.withTitle(event.title())
                            .withDescription(event.description())
                            .withDate(event.date());
                    return gateway.save(updated);
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    @Override
    public void delete(UUID id) {
        if (gateway.findById(id).isEmpty()) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        gateway.deleteById(id);
    }
}