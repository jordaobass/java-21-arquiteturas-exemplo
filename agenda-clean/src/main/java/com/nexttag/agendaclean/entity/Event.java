package com.nexttag.agendaclean.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public record Event(UUID id, String title, String description, LocalDateTime date) {
    public Event(String title, String description, LocalDateTime date) {
        this(UUID.randomUUID(), title, description, date);
    }

    public Event withId(UUID id) {
        return new Event(id, this.title, this.description, this.date);
    }

    public Event withTitle(String title) {
        return new Event(this.id, title, this.description, this.date);
    }

    public Event withDescription(String description) {
        return new Event(this.id, this.title, description, this.date);
    }

    public Event withDate(LocalDateTime date) {
        return new Event(this.id, this.title, this.description, date);
    }
}