package com.nexttag.agendacqrs.command.handler;

import com.nexttag.agendacqrs.command.model.EventCommand;
import com.nexttag.agendacqrs.domain.event.EventUpdated;
import com.nexttag.agendacqrs.infrastructure.repository.CommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Handler responsável por processar comandos de atualização de eventos.
 * <p>
 * Esta classe implementa o padrão Command Handler do CQRS, sendo responsável por:
 * <ul>
 *     <li>Atualizar eventos existentes no repositório de comandos</li>
 *     <li>Publicar eventos de domínio para notificar outras partes do sistema</li>
 *     <li>Garantir a consistência da operação de atualização</li>
 * </ul>
 * </p>
 *
 * <p>
 * O handler segue os princípios do CQRS (Command Query Responsibility Segregation),
 * sendo dedicado exclusivamente à operação de escrita/atualização de eventos.
 * </p>
 */
@Component
public class UpdateEventHandler {

    /**
     * Repositório para persistência de comandos.
     * Utilizado para atualizar o estado do evento no lado de escrita.
     */
    private final CommandRepository repository;

    /**
     * Publicador de eventos da aplicação Spring.
     * Utilizado para notificar outras partes do sistema sobre a atualização do evento.
     */
    private final ApplicationEventPublisher publisher;

    /**
     * Construtor para injeção de dependências.
     *
     * @param repository o repositório de comandos para persistência
     * @param publisher o publicador de eventos da aplicação Spring
     */
    public UpdateEventHandler(CommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * Processa um comando de atualização de evento.
     * <p>
     * Este método executa as seguintes operações em ordem:
     * <ol>
     *     <li>Atualiza o evento no repositório de comandos com os novos dados</li>
     *     <li>Publica um evento de domínio {@link EventUpdated} para notificar o sistema</li>
     * </ol>
     * </p>
     *
     * <p>
     * O evento publicado será capturado pelo {@code QueryEventHandler} para
     * sincronizar o lado de leitura (query) com as mudanças do lado de escrita (command),
     * atualizando a representação correspondente no repositório de consultas.
     * </p>
     *
     * @param id o identificador único do evento a ser atualizado
     * @param command o comando contendo os novos dados do evento
     * @throws IllegalArgumentException se o id ou command forem null ou contiverem dados inválidos
     * @throws org.springframework.dao.DataAccessException se ocorrer erro na persistência

     */
    public void handle(UUID id, EventCommand command) {
        // Atualiza o evento no repositório de escrita
        repository.update(id, command);

        // Publica evento de domínio para sincronização com o lado de query
        publisher.publishEvent(new EventUpdated(id, command.title(), command.description(), command.date()));
    }
}