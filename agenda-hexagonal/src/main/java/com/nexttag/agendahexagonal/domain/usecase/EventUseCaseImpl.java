package com.nexttag.agendahexagonal.domain.usecase;

import com.nexttag.agendahexagonal.domain.entity.Event;
import com.nexttag.agendahexagonal.domain.port.in.EventUseCase;
import com.nexttag.agendahexagonal.domain.port.out.EventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventUseCaseImpl implements EventUseCase {
    private final EventRepositoryPort repositoryPort;

    public EventUseCaseImpl(EventRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Event create(Event event) {
        return repositoryPort.save(event);
    }

    @Override
    public List<Event> listAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Event update(UUID id, Event event) {
        return repositoryPort.findById(id)
                .map(existing -> {
                    Event updated = existing.withTitle(event.title())
                            .withDescription(event.description())
                            .withDate(event.date());
                    return repositoryPort.save(updated);
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    @Override
    public void delete(UUID id) {
        if (repositoryPort.findById(id).isEmpty()) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        repositoryPort.deleteById(id);
    }
}