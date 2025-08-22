package com.nexttag.agendacqrs.command.handler;

import com.nexttag.agendacqrs.domain.event.EventDeleted;
import com.nexttag.agendacqrs.infrastructure.repository.CommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Handler responsável por processar comandos de exclusão de eventos.
 * <p>
 * Esta classe implementa o padrão Command Handler do CQRS, sendo responsável por:
 * <ul>
 *     <li>Remover eventos existentes do repositório de comandos</li>
 *     <li>Publicar eventos de domínio para notificar outras partes do sistema</li>
 *     <li>Garantir a consistência da operação de exclusão</li>
 * </ul>
 * </p>
 * <p>
 * O handler segue os princípios do CQRS (Command Query Responsibility Segregation),
 * sendo dedicado exclusivamente à operação de escrita/exclusão de eventos.
 * </p>
 */
@Component
public class DeleteEventHandler {

    /**
     * Repositório para persistência de comandos.
     * Utilizado para remover o estado do evento no lado de escrita.
     */
    private final CommandRepository repository;

    /**
     * Publicador de eventos da aplicação Spring.
     * Utilizado para notificar outras partes do sistema sobre a exclusão do evento.
     */
    private final ApplicationEventPublisher publisher;

    /**
     * Construtor para injeção de dependências.
     *
     * @param repository o repositório de comandos para persistência
     * @param publisher  o publicador de eventos da aplicação Spring
     */
    public DeleteEventHandler(CommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * Processa um comando de exclusão de evento.
     * <p>
     * Este método executa as seguintes operações em ordem:
     * <ol>
     *     <li>Remove o evento do repositório de comandos</li>
     *     <li>Publica um evento de domínio {@link EventDeleted} para notificar o sistema</li>
     * </ol>
     * </p>
     *
     * <p>
     * O evento publicado será capturado pelo {@code QueryEventHandler} para
     * sincronizar o lado de leitura (query) com as mudanças do lado de escrita (command),
     * removendo a representação correspondente do repositório de consultas.
     * </p>
     *
     * @param id o identificador único do evento a ser excluído
     * @throws IllegalArgumentException                    se o id for null
     * @throws org.springframework.dao.DataAccessException se ocorrer erro na persistência
     *
     */
    public void handle(UUID id) {
        // Remove o evento do repositório de escrita
        repository.delete(id);

        // Publica evento de domínio para sincronização com o lado de query
        publisher.publishEvent(new EventDeleted(id));
    }
}