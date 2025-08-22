package com.nexttag.agendalayered.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Modelo/Entidade representando um Evento na agenda.
 *
 * Em arquitetura em camadas, esta classe serve tanto como entidade de domínio quanto como mapeamento JPA para o banco.
 * Contém os dados principais e é usada em todas as camadas.
 *
 * Responsabilidades:
 * - Armazenar atributos do evento (id, título, descrição, data).
 * - Fornecer getters e setters para manipulação.
 */
@Entity
public class Event {
    @Id
    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private LocalDateTime date;

    /**
     * Construtor padrão.
     */
    public Event() {}

    /**
     * Construtor completo.
     *
     * @param title Título do evento.
     * @param description Descrição do evento.
     * @param date Data do evento.
     */
    public Event(String title, String description, LocalDateTime date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}