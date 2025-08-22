
package com.nexttag.agendacqrs.command.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Comando representando uma operação de escrita de evento na arquitetura CQRS.
 * <p>
 * Este record encapsula todos os dados necessários para operações de comando (escrita)
 * relacionadas a eventos, incluindo criação, atualização e exclusão. Serve como
 * objeto de transferência de dados entre a camada de API e os handlers de comando.
 * </p>
 *
 * <p>
 * O comando segue os princípios do CQRS (Command Query Responsibility Segregation),
 * sendo utilizado exclusivamente no lado de escrita para transportar dados
 * de entrada para os handlers de comando.
 * </p>
 *
 * <p>
 * A classe oferece dois construtores: um completo com ID especificado e outro
 * de conveniência que gera automaticamente um UUID único para novos eventos.
 * </p>
 *
 * @param id identificador único do evento
 * @param title título do evento
 * @param description descrição detalhada do evento
 * @param date data e hora do evento
 */
public record EventCommand(UUID id, String title, String description, LocalDateTime date) {

    /**
     * Construtor de conveniência para criação de novos eventos.
     * <p>
     * Gera automaticamente um UUID único para o evento, facilitando
     * a criação de comandos para novos eventos sem necessidade de
     * especificar manualmente um identificador.
     * </p>
     *
     * @param title título do evento
     * @param description descrição detalhada do evento
     * @param date data e hora do evento
     */
    public EventCommand(String title, String description, LocalDateTime date) {
        this(UUID.randomUUID(), title, description, date);
    }
}