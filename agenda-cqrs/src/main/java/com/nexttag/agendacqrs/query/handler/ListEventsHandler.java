package com.nexttag.agendacqrs.query.handler;

import com.nexttag.agendacqrs.infrastructure.repository.QueryRepository;
import com.nexttag.agendacqrs.query.model.EventQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListEventsHandler {
    private final QueryRepository repository;

    public ListEventsHandler(QueryRepository repository) {
        this.repository = repository;
    }

    public List<EventQuery> handle() {
        return repository.findAll();
    }
}