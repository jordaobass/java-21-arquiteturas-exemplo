package com.nexttag.agendacqrs.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de domínio representando a atualização de um evento na agenda.
 * <p>
 * Este record é publicado quando um evento é atualizado através do lado de comando (CQRS),
 * permitindo que outros componentes do sistema, especialmente o lado de query, sejam
 * notificados e possam sincronizar seus dados com as informações atualizadas do evento.
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
 * o repositório de consultas com os dados atualizados do evento.
 * </p>
 *
 * @param id identificador único do evento que foi atualizado
 * @param title novo título do evento
 * @param description nova descrição detalhada do evento
 * @param date nova data e hora do evento
 */

public record EventUpdated(UUID id, String title, String description, LocalDateTime date) {}