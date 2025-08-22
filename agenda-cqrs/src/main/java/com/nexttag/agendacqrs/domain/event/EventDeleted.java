package com.nexttag.agendacqrs.domain.event;

import java.util.UUID;

/**
 * Evento de domínio representando a exclusão de um evento da agenda.
 * <p>
 * Este record é publicado quando um evento é excluído através do lado de comando (CQRS),
 * permitindo que outros componentes do sistema, especialmente o lado de query, sejam
 * notificados e possam remover adequadamente os dados correspondentes de seus repositórios.
 * </p>
 *
 * <p>
 * O evento segue os princípios do Event Sourcing básico implementado na aplicação,
 * onde mudanças no estado são propagadas através de eventos de domínio usando
 * o sistema de eventos do Spring Framework.
 * </p>
 *
 * <p>
 * Este evento é capturado pelo {@code QueryEventHandler} para sincronizar
 * o repositório de consultas, removendo a representação do evento excluído.
 * </p>
 *
 * @param id identificador único do evento que foi excluído
 *
 */

public record EventDeleted(UUID id) {}