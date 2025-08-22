package com.nexttag.agendacqrs.api;

import com.nexttag.agendacqrs.api.dto.EventDto;
import com.nexttag.agendacqrs.query.handler.ListEventsHandler;
import com.nexttag.agendacqrs.query.model.EventQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST responsável por expor endpoints para consultas de leitura de eventos.
 * <p>
 * Esta classe implementa a camada de apresentação (API) para o lado de consulta (read)
 * da arquitetura CQRS, sendo responsável por:
 * <ul>
 *     <li>Receber requisições HTTP para operações de leitura (GET)</li>
 *     <li>Delegar o processamento para os handlers de consulta</li>
 *     <li>Converter modelos de consulta em DTOs apropriados</li>
 *     <li>Retornar respostas HTTP com os dados solicitados</li>
 * </ul>
 * </p>
 *
 * <p>
 * O controlador segue os princípios do CQRS (Command Query Responsibility Segregation),
 * sendo dedicado exclusivamente às operações de leitura, enquanto o {@code CommandController}
 * é responsável pelas operações de escrita.
 * </p>
 *
 * <p>
 * Todos os endpoints estão mapeados sob o prefixo {@code /queries/events}, seguindo
 * a convenção de separação clara entre comandos e consultas na API REST.
 * </p>
 *
 * <p>
 * Este controlador acessa dados otimizados para leitura, que são mantidos sincronizados
 * com o lado de comando através de eventos de domínio processados pelo {@code QueryEventHandler}.
 * </p>
 */
@RestController
@RequestMapping("/queries/events")
public class QueryController {

    /**
     * Handler para processar consultas de listagem de eventos.
     */
    private final ListEventsHandler handler;

    /**
     * Construtor para injeção de dependências.
     *
     * @param handler handler para consultas de listagem
     */
    public QueryController(ListEventsHandler handler) {
        this.handler = handler;
    }

    /**
     * Endpoint para listagem de todos os eventos.
     * <p>
     * Recupera todos os eventos disponíveis no repositório de consultas,
     * converte os modelos de consulta para DTOs e retorna a lista completa.
     * Este endpoint é otimizado para performance de leitura.
     * </p>
     *
     * <p>
     * Os dados retornados são provenientes do repositório de consultas (query side),
     * que é mantido sincronizado com o lado de comando através de eventos de domínio.
     * Isso garante consistência eventual entre os lados de escrita e leitura.
     * </p>
     *
     * @return ResponseEntity com status 200 (OK) e lista de EventDto contendo todos os eventos
     * @throws org.springframework.dao.DataAccessException se ocorrer erro no acesso aos dados
     */
    @GetMapping
    public ResponseEntity<List<EventDto>> listAll() {
        // Delega processamento para o handler de consulta
        List<EventQuery> queries = handler.handle();

        // Converte modelos de consulta para DTOs de resposta
        List<EventDto> dtos = queries.stream()
                .map(q -> new EventDto(q.id(), q.title(), q.description(), q.date()))
                .collect(Collectors.toList());

        // Retorna resposta com status OK e lista de eventos
        return ResponseEntity.ok(dtos);
    }
}