package com.nexttag.agendalayered.repository;

import com.nexttag.agendalayered.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório para acesso a dados de Eventos.
 * <p>
 * Na camada de dados, esta interface estende JpaRepository para operações CRUD automáticas no banco.
 * É o ponto de contato direto com a persistência (H2 in-memory).
 * <p>
 * Responsabilidades:
 * - Fornecer métodos para salvar, listar, buscar e deletar eventos.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
}