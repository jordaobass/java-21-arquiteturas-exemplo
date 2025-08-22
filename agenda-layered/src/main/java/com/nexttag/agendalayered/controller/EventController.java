package com.nexttag.agendalayered.controller;

import com.nexttag.agendalayered.model.Event;
import com.nexttag.agendalayered.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para endpoints REST de Eventos.
 *
 * Na camada de apresentação, esta classe lida com requisições HTTP,
 * delegando para o serviço e retornando respostas.
 *
 * Responsabilidades:
 * - Mapear rotas para operações CRUD.
 * - Converter dados de entrada/saída.
 */
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    /**
     * Construtor com injeção do serviço.
     *
     * @param service Serviço de negócio.
     */
    public EventController(EventService service) {
        this.service = service;
    }

    /**
     * Endpoint para criar evento.
     *
     * @param event Dados do evento.
     * @return Response com evento criado.
     */
    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(event));
    }

    /**
     * Endpoint para listar eventos.
     *
     * @return Response com lista de eventos.
     */
    @GetMapping
    public ResponseEntity<List<Event>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    /**
     * Endpoint para atualizar evento.
     *
     * @param id ID do evento.
     * @param event Dados atualizados.
     * @return Response com evento atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable UUID id, @RequestBody Event event) {
        return ResponseEntity.ok(service.update(id, event));
    }

    /**
     * Endpoint para deletar evento.
     *
     * @param id ID do evento.
     * @return Response sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}