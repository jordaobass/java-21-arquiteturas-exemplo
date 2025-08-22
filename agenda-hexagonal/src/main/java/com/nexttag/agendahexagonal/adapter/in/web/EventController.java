package com.nexttag.agendahexagonal.adapter.in.web;

import com.nexttag.agendahexagonal.adapter.in.web.dto.EventDto;
import com.nexttag.agendahexagonal.domain.entity.Event;
import com.nexttag.agendahexagonal.domain.port.in.EventUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventUseCase eventUseCase;

    public EventController(EventUseCase eventUseCase) {
        this.eventUseCase = eventUseCase;
    }

    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        Event event = new Event(dto.title(), dto.description(), dto.date());
        Event created = eventUseCase.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> listAll() {
        List<Event> events = eventUseCase.listAll();
        List<EventDto> dtos = events.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable UUID id, @RequestBody EventDto dto) {
        Event event = new Event(dto.title(), dto.description(), dto.date());
        Event updated = eventUseCase.update(id, event);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EventDto toDto(Event event) {
        return new EventDto(event.id(), event.title(), event.description(), event.date());
    }
}