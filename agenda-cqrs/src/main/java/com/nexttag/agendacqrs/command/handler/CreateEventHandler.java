
package com.nexttag.agendacqrs.command.handler;

import com.nexttag.agendacqrs.command.model.EventCommand;
import com.nexttag.agendacqrs.domain.event.EventCreated;
import com.nexttag.agendacqrs.infrastructure.repository.CommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Handler responsável por processar comandos de criação de eventos.
 * <p>
 * Esta classe implementa o padrão Command Handler do CQRS, sendo responsável por:
 * <ul>
 *     <li>Persistir novos eventos no repositório de comandos</li>
 *     <li>Publicar eventos de domínio para notificar outras partes do sistema</li>
 *     <li>Retornar o identificador único do evento criado</li>
 * </ul>
 * </p>
 *
 * <p>
 * O handler segue os princípios do CQRS (Command Query Responsibility Segregation),
 * sendo dedicado exclusivamente à operação de escrita/criação de eventos.
 * </p>
 *
 * @author NextTag Development Team
 * @version 1.0
 * @since 1.0
 */
@Component
public class CreateEventHandler {

    /**
     * Repositório para persistência de comandos.
     * Utilizado para armazenar o estado do evento no lado de escrita.
     */
    private final CommandRepository repository;

    /**
     * Publicador de eventos da aplicação Spring.
     * Utilizado para notificar outras partes do sistema sobre a criação do evento.
     */
    private final ApplicationEventPublisher publisher;

    /**
     * Construtor para injeção de dependências.
     *
     * @param repository o repositório de comandos para persistência
     * @param publisher o publicador de eventos da aplicação Spring
     */
    public CreateEventHandler(CommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * Processa um comando de criação de evento.
     * <p>
     * Este método executa as seguintes operações em ordem:
     * <ol>
     *     <li>Persiste o comando no repositório de comandos</li>
     *     <li>Publica um evento de domínio {@link EventCreated} para notificar o sistema</li>
     *     <li>Retorna o identificador único do evento criado</li>
     * </ol>
     * </p>
     *
     * <p>
     * O evento publicado será capturado pelo {@code QueryEventHandler} para
     * sincronizar o lado de leitura (query) com as mudanças do lado de escrita (command).
     * </p>
     *
     * @param command o comando contendo os dados do evento a ser criado
     * @return o UUID do evento criado
     * @throws IllegalArgumentException se o command for null ou contiver dados inválidos
     * @throws org.springframework.dao.DataAccessException se ocorrer erro na persistência
     *
     * @see EventCommand
     * @see EventCreated
     * @see com.nexttag.agendacqrs.infrastructure.event.QueryEventHandler#onEventCreated(EventCreated)
     */
    public UUID handle(EventCommand command) {
        // Persiste o comando no repositório de escrita
        repository.save(command);

        // Publica evento de domínio para sincronização com o lado de query
        publisher.publishEvent(new EventCreated(command.id(), command.title(), command.description(), command.date()));

        // Retorna o identificador único do evento criado
        return command.id();
    }
}