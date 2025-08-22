
package com.nexttag.agendacqrs.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de domínio representando a criação de um evento na agenda.
 * <p>
 * Este record é publicado quando um novo evento é criado através do lado de comando (CQRS),
 * permitindo que outros componentes do sistema, especialmente o lado de query, sejam
 * notificados e possam sincronizar seus dados adequadamente.
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
 * o repositório de consultas com o novo evento criado.
 * </p>
 *
 * @param id identificador único do evento criado
 * @param title título do evento
 * @param description descrição detalhada do evento
 * @param date data e hora do evento
 */
public record EventCreated(UUID id, String title, String description, LocalDateTime date) {}