package com.nexttag.agendacqrs.infrastructure.repository;

import com.nexttag.agendacqrs.query.model.EventQuery;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositório em memória para operações de consulta no lado de query da arquitetura CQRS.
 * <p>
 * Esta classe implementa um repositório otimizado para operações de leitura,
 * armazenando dados em memória para máxima performance nas consultas.
 * É utilizada exclusivamente pelo lado de consulta (query) da arquitetura CQRS.
 * </p>
 *
 * <p>
 * O repositório é sincronizado com o lado de comando através de eventos de domínio
 * processados pelo {@code QueryEventHandler}, garantindo consistência eventual
 * entre os dados de escrita e leitura.
 * </p>
 *
 * <p>
 * Utiliza {@code ConcurrentHashMap} para garantir thread-safety em operações
 * concorrentes, permitindo múltiplas leituras simultâneas sem bloqueio.
 * </p>
 *
 * <p>
 * <strong>Nota:</strong> Em ambiente de produção, considere utilizar soluções
 * de cache distribuído como Redis para escalabilidade e persistência.
 * </p>
 */
@Component
public class QueryRepository {

    /**
     * Armazenamento em memória thread-safe para os dados de consulta.
     * Utiliza UUID como chave para acesso eficiente aos eventos.
     */
    private final Map<UUID, EventQuery> storage = new ConcurrentHashMap<>();

    /**
     * Adiciona um novo evento ao repositório de consultas.
     * <p>
     * Método utilizado pelo {@code QueryEventHandler} quando um evento
     * é criado no lado de comando, mantendo sincronização entre os lados.
     * </p>
     *
     * @param eventQuery objeto de consulta a ser adicionado
     * @throws IllegalArgumentException se eventQuery for null ou já existir
     */
    public void add(EventQuery eventQuery) {
        if (eventQuery == null) {
            throw new IllegalArgumentException("EventQuery cannot be null");
        }

        // Adiciona o evento ao armazenamento em memória
        storage.put(eventQuery.id(), eventQuery);
    }

    /**
     * Atualiza um evento existente no repositório de consultas.
     * <p>
     * Método utilizado pelo {@code QueryEventHandler} quando um evento
     * é atualizado no lado de comando, mantendo sincronização entre os lados.
     * </p>
     *
     * @param eventQuery objeto de consulta com dados atualizados
     * @throws IllegalArgumentException se eventQuery for null
     */
    public void update(EventQuery eventQuery) {
        if (eventQuery == null) {
            throw new IllegalArgumentException("EventQuery cannot be null");
        }

        // Atualiza o evento no armazenamento (substitui se existir)
        storage.put(eventQuery.id(), eventQuery);
    }

    /**
     * Remove um evento do repositório de consultas.
     * <p>
     * Método utilizado pelo {@code QueryEventHandler} quando um evento
     * é excluído no lado de comando, mantendo sincronização entre os lados.
     * </p>
     *
     * @param id identificador único do evento a ser removido
     * @throws IllegalArgumentException se id for null
     *
     * @see com.nexttag.agendacqrs.infrastructure.event.QueryEventHandler#onEventDeleted
     */
    public void remove(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        // Remove o evento do armazenamento
        storage.remove(id);
    }

    /**
     * Retorna todos os eventos disponíveis para consulta.
     * <p>
     * Método otimizado para leitura, retornando uma lista imutável
     * de todos os eventos armazenados. Operação thread-safe e eficiente
     * para consultas de listagem.
     * </p>
     *
     * @return lista imutável contendo todos os eventos de consulta
     *
     * @see com.nexttag.agendacqrs.query.handler.ListEventsHandler#handle()
     */
    public List<EventQuery> findAll() {
        // Retorna cópia imutável dos valores para evitar modificações externas
        return new ArrayList<>(storage.values());
    }

    /**
     * Busca um evento específico pelo seu identificador.
     * <p>
     * Método utilitário para consultas por ID, retornando um {@code Optional}
     * para tratamento seguro de casos onde o evento não existe.
     * </p>
     *
     * @param id identificador único do evento
     * @return Optional contendo o evento se encontrado, vazio caso contrário
     * @throws IllegalArgumentException se id for null
     */
    public Optional<EventQuery> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Retorna o número total de eventos armazenados.
     * <p>
     * Método utilitário para estatísticas e monitoramento do repositório.
     * </p>
     *
     * @return quantidade total de eventos no repositório
     */
    public int count() {
        return storage.size();
    }

    /**
     * Remove todos os eventos do repositório.
     * <p>
     * Método utilitário para limpeza do repositório, útil em cenários
     * de teste ou reinicialização do sistema.
     * </p>
     */
    public void clear() {
        storage.clear();
    }
}