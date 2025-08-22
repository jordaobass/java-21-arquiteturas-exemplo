package com.nexttag.agendacqrs.infrastructure.repository;

import com.nexttag.agendacqrs.command.model.EventCommand;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * Repositório para operações de persistência no lado de comando da arquitetura CQRS.
 * <p>
 * Esta classe atua como uma camada de abstração sobre o {@code CommandJpaRepository},
 * fornecendo operações de alto nível para persistência de comandos de eventos.
 * Implementa o padrão Repository, encapsulando a lógica de mapeamento entre
 * objetos de domínio ({@code EventCommand}) e entidades JPA ({@code CommandEntity}).
 * </p>
 *
 * <p>
 * O repositório é dedicado exclusivamente ao lado de escrita (command) da arquitetura
 * CQRS, garantindo operações transacionais consistentes e isolamento dos dados de comando.
 * </p>
 *
 * <p>
 * As operações incluem criação, atualização e exclusão de eventos, com tratamento
 * adequado de erros e validações de existência para operações críticas.
 * </p>
 */
@Component
public class CommandRepository {

    /**
     * Repositório JPA para acesso aos dados persistidos.
     */
    private final CommandJpaRepository jpaRepository;

    /**
     * Construtor para injeção de dependências.
     *
     * @param jpaRepository repositório JPA para operações de persistência
     */
    public CommandRepository(CommandJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Persiste um novo evento no repositório de comandos.
     * <p>
     * Converte o comando de domínio em entidade JPA e persiste no banco de dados.
     * Esta operação é utilizada para criação de novos eventos.
     * </p>
     *
     * @param command comando contendo os dados do evento a ser persistido
     * @throws org.springframework.dao.DataAccessException se ocorrer erro na persistência
     *
     * @see com.nexttag.agendacqrs.command.handler.CreateEventHandler#handle(EventCommand)
     */
    public void save(EventCommand command) {
        // Converte comando para entidade JPA
        CommandEntity entity = toEntity(command);

        // Persiste a entidade no banco de dados
        jpaRepository.save(entity);
    }

    /**
     * Atualiza um evento existente no repositório de comandos.
     * <p>
     * Busca a entidade pelo ID fornecido e atualiza seus campos com os dados
     * do comando. Lança exceção se o evento não for encontrado.
     * </p>
     *
     * @param id identificador único do evento a ser atualizado
     * @param command comando contendo os novos dados do evento
     * @throws RuntimeException se o evento não for encontrado
     * @throws org.springframework.dao.DataAccessException se ocorrer erro na persistência
     *
     * @see com.nexttag.agendacqrs.command.handler.UpdateEventHandler#handle(UUID, EventCommand)
     */
    public void update(UUID id, EventCommand command) {
        jpaRepository.findById(id)
                .ifPresentOrElse(
                        entity -> {
                            // Atualiza os campos da entidade existente
                            entity.setTitle(command.title());
                            entity.setDescription(command.description());
                            entity.setDate(command.date());

                            // Persiste as alterações
                            jpaRepository.save(entity);
                        },
                        () -> {
                            throw new RuntimeException("Event not found with id: " + id);
                        }
                );
    }

    /**
     * Remove um evento do repositório de comandos.
     * <p>
     * Verifica se o evento existe antes de tentar removê-lo, lançando
     * exceção caso não seja encontrado. Garante consistência nas operações de exclusão.
     * </p>
     *
     * @param id identificador único do evento a ser removido
     * @throws RuntimeException se o evento não for encontrado
     * @throws org.springframework.dao.DataAccessException se ocorrer erro na persistência
     *
     * @see com.nexttag.agendacqrs.command.handler.DeleteEventHandler#handle(UUID)
     */
    public void delete(UUID id) {
        // Verifica se o evento existe antes de tentar deletar
        if (jpaRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Event not found with id: " + id);
        }

        // Remove o evento do banco de dados
        jpaRepository.deleteById(id);
    }

    /**
     * Converte um comando de domínio em entidade JPA.
     * <p>
     * Método utilitário privado para mapeamento entre objetos de domínio
     * e entidades de persistência, mantendo a separação de responsabilidades.
     * </p>
     *
     * @param command comando a ser convertido
     * @return entidade JPA correspondente
     */
    private CommandEntity toEntity(EventCommand command) {
        return new CommandEntity(command.id(), command.title(), command.description(), command.date());
    }
}