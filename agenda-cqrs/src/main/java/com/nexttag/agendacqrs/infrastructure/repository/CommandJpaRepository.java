package com.nexttag.agendacqrs.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/**
 * Repositório JPA para operações de persistência de comandos de eventos.
 * <p>
 * Esta interface estende {@code JpaRepository} fornecendo operações CRUD
 * automáticas para a entidade {@code CommandEntity}. É utilizada exclusivamente
 * pelo lado de comando (write) da arquitetura CQRS para persistência no banco H2.
 * </p>
 *
 * <p>
 * O repositório é otimizado para operações de escrita consistente, garantindo
 * a integridade transacional das operações de comando. Utiliza UUID como
 * tipo de identificador, facilitando a distribuição e evitando conflitos.
 * </p>
 *
 * <p>
 * Esta interface é automaticamente implementada pelo Spring Data JPA,
 * fornecendo métodos como {@code save()}, {@code findById()}, {@code deleteById()},
 * entre outros, sem necessidade de implementação manual.
 * </p>

 */
@Repository
public interface CommandJpaRepository extends JpaRepository<CommandEntity, UUID> {
    // Métodos CRUD automáticos fornecidos pelo JpaRepository:
    // - save(CommandEntity entity)
    // - findById(UUID id)
    // - findAll()
    // - deleteById(UUID id)
    // - delete(CommandEntity entity)
    // - existsById(UUID id)
    // - count()
}