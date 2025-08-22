package com.nexttag.agendacqrs.infrastructure.repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade JPA representando um evento no lado de comando da arquitetura CQRS.
 * <p>
 * Esta classe mapeia os dados de evento para persistência no banco de dados
 * relacional (H2), sendo utilizada exclusivamente pelo lado de escrita (command)
 * da aplicação. A entidade é otimizada para operações de escrita consistente.
 * </p>
 *
 * <p>
 * A entidade é mapeada para a tabela {@code command_events} e utiliza UUID
 * como chave primária, garantindo identificadores únicos globalmente e
 * facilitando a distribuição em ambientes escaláveis.
 * </p>
 *
 * <p>
 * Esta classe segue os princípios do CQRS, sendo separada dos modelos de
 * consulta ({@code EventQuery}) para permitir otimizações específicas
 * para cada tipo de operação.
 * </p>
 */
@Entity
@Table(name = "command_events")
public class CommandEntity {

    /**
     * Identificador único do evento.
     * Chave primária da tabela, utilizando UUID para garantir unicidade global.
     */
    @Id
    private UUID id;

    /**
     * Título do evento.
     */
    private String title;

    /**
     * Descrição detalhada do evento.
     */
    private String description;

    /**
     * Data e hora do evento.
     */
    private LocalDateTime date;

    /**
     * Construtor padrão exigido pelo JPA.
     * Não deve ser utilizado diretamente no código da aplicação.
     */
    public CommandEntity() {
    }

    /**
     * Construtor completo para criação de entidades com todos os campos.
     *
     * @param id          identificador único do evento
     * @param title       título do evento
     * @param description descrição detalhada do evento
     * @param date        data e hora do evento
     */
    public CommandEntity(UUID id, String title, String description, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // Getters and Setters

    /**
     * Obtém o identificador único do evento.
     *
     * @return UUID do evento
     */
    public UUID getId() {
        return id;
    }

    /**
     * Define o identificador único do evento.
     *
     * @param id UUID do evento
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Obtém o título do evento.
     *
     * @return título do evento
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define o título do evento.
     *
     * @param title título do evento
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtém a descrição detalhada do evento.
     *
     * @return descrição do evento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição detalhada do evento.
     *
     * @param description descrição do evento
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtém a data e hora do evento.
     *
     * @return data e hora do evento
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Define a data e hora do evento.
     *
     * @param date data e hora do evento
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}