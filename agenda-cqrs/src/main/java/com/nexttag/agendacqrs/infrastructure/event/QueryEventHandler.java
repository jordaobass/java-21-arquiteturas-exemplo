package com.nexttag.agendacqrs.infrastructure.event;

import com.nexttag.agendacqrs.domain.event.EventCreated;
import com.nexttag.agendacqrs.domain.event.EventDeleted;
import com.nexttag.agendacqrs.domain.event.EventUpdated;
import com.nexttag.agendacqrs.infrastructure.repository.QueryRepository;
import com.nexttag.agendacqrs.query.model.EventQuery;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Manipulador de eventos para sincronização do lado de query na arquitetura CQRS.
 * <p>
 * Esta classe é responsável por escutar eventos de domínio publicados pelo lado de comando
 * e atualizar o repositório de consultas para manter a consistência eventual entre os modelos
 * de escrita e leitura.
 * </p>
 * <p>
 * Os eventos tratados incluem criação, atualização e exclusão de eventos, garantindo que
 * o modelo de query permaneça sincronizado com as mudanças realizadas no modelo de comando.
 * </p>
 *
 * @author Sistema Agenda CQRS
 * @version 1.0
 * @since 1.0
 */
@Component
public class QueryEventHandler {

    private final QueryRepository queryRepository;

    /**
     * Construtor para injeção de dependência do repositório de queries.
     *
     * @param queryRepository o repositório responsável por gerenciar os dados de consulta
     * @throws IllegalArgumentException se o repositório for nulo
     */
    public QueryEventHandler(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    /**
     * Manipula eventos de criação de evento.
     * <p>
     * Quando um novo evento é criado no lado de comando, este método adiciona
     * a representação correspondente no repositório de consultas.
     * </p>
     *
     * @param event o evento de criação contendo os dados do novo evento
     * @see EventCreated
     */
    @EventListener
    public void onEventCreated(EventCreated event) {
        queryRepository.add(toEventQuery(event.id(), event.title(), event.description(), event.date()));
    }

    /**
     * Manipula eventos de atualização de evento.
     * <p>
     * Quando um evento é atualizado no lado de comando, este método atualiza
     * a representação correspondente no repositório de consultas.
     * </p>
     *
     * @param event o evento de atualização contendo os novos dados do evento
     * @see EventUpdated
     */
    @EventListener
    public void onEventUpdated(EventUpdated event) {
        queryRepository.update(toEventQuery(event.id(), event.title(), event.description(), event.date()));
    }

    /**
     * Manipula eventos de exclusão de evento.
     * <p>
     * Quando um evento é excluído no lado de comando, este método remove
     * a representação correspondente do repositório de consultas.
     * </p>
     *
     * @param event o evento de exclusão contendo o ID do evento a ser removido
     * @see EventDeleted
     */
    @EventListener
    public void onEventDeleted(EventDeleted event) {
        queryRepository.remove(event.id());
    }

    /**
     * Converte os dados de um evento de domínio em um objeto EventQuery.
     * <p>
     * Este método auxiliar centraliza a criação de instâncias EventQuery,
     * eliminando duplicação de código e facilitando futuras manutenções.
     * </p>
     *
     * @param id o identificador único do evento
     * @param title o título do evento
     * @param description a descrição do evento
     * @param date a data e hora do evento
     * @return uma nova instância de EventQuery com os dados fornecidos
     * @see EventQuery
     */
    private EventQuery toEventQuery(UUID id, String title, String description, LocalDateTime date) {
        return new EventQuery(id, title, description, date);
    }
}