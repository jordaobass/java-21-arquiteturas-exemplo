package com.nexttag.agendaclean.framework.adapter.web;

import com.nexttag.agendaclean.framework.adapter.web.dto.EventDto;
import com.nexttag.agendaclean.usecase.EventUseCase;
import com.nexttag.agendaclean.entity.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventUseCase useCase;

    public EventController(EventUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        Event event = new Event(dto.title(), dto.description(), dto.date());
        Event created = useCase.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> listAll() {
        List<Event> events = useCase.listAll();
        List<EventDto> dtos = events.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable UUID id, @RequestBody EventDto dto) {
        Event event = new Event(dto.title(), dto.description(), dto.date());
        Event updated = useCase.update(id, event);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EventDto toDto(Event event) {
        return new EventDto(event.id(), event.title(), event.description(), event.date());
    }
}