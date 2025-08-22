
package com.nexttag.agendacqrs.api;

import com.nexttag.agendacqrs.api.dto.EventDto;
import com.nexttag.agendacqrs.command.handler.CreateEventHandler;
import com.nexttag.agendacqrs.command.handler.DeleteEventHandler;
import com.nexttag.agendacqrs.command.handler.UpdateEventHandler;
import com.nexttag.agendacqrs.command.model.EventCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST responsável por expor endpoints para comandos de escrita de eventos.
 * <p>
 * Esta classe implementa a camada de apresentação (API) para o lado de comando (write)
 * da arquitetura CQRS, sendo responsável por:
 * <ul>
 *     <li>Receber requisições HTTP para operações de escrita (CREATE, UPDATE, DELETE)</li>
 *     <li>Converter DTOs em comandos apropriados</li>
 *     <li>Delegar o processamento para os handlers específicos</li>
 *     <li>Retornar respostas HTTP adequadas</li>
 * </ul>
 * </p>
 *
 * <p>
 * O controlador segue os princípios do CQRS (Command Query Responsibility Segregation),
 * sendo dedicado exclusivamente às operações de escrita, enquanto o {@code QueryController}
 * é responsável pelas operações de leitura.
 * </p>
 *
 * <p>
 * Todos os endpoints estão mapeados sob o prefixo {@code /commands/events}, seguindo
 * a convenção de separação clara entre comandos e consultas na API REST.
 * </p>
 */
@RestController
@RequestMapping("/commands/events")
public class CommandController {

    /**
     * Handler para processar comandos de criação de eventos.
     */
    private final CreateEventHandler createHandler;

    /**
     * Handler para processar comandos de atualização de eventos.
     */
    private final UpdateEventHandler updateHandler;

    /**
     * Handler para processar comandos de exclusão de eventos.
     */
    private final DeleteEventHandler deleteHandler;

    /**
     * Construtor para injeção de dependências.
     *
     * @param createHandler handler para comandos de criação
     * @param updateHandler handler para comandos de atualização
     * @param deleteHandler handler para comandos de exclusão
     */
    public CommandController(CreateEventHandler createHandler, UpdateEventHandler updateHandler, DeleteEventHandler deleteHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
    }

    /**
     * Endpoint para criação de novos eventos.
     * <p>
     * Recebe um DTO com os dados do evento, converte para comando e delega
     * o processamento para o handler apropriado. Retorna o ID do evento criado.
     * </p>
     *
     * @param dto dados do evento a ser criado
     * @return ResponseEntity com status 201 (CREATED) e o UUID do evento criado
     * @throws IllegalArgumentException se o DTO contiver dados inválidos
     */
    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody EventDto dto) {
        // Converte DTO para comando de domínio
        EventCommand command = new EventCommand(dto.title(), dto.description(), dto.date());

        // Delega processamento para o handler
        UUID id = createHandler.handle(command);

        // Retorna resposta com status CREATED e ID do evento
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    /**
     * Endpoint para atualização de eventos existentes.
     * <p>
     * Recebe o ID do evento a ser atualizado e um DTO com os novos dados,
     * converte para comando e delega o processamento para o handler apropriado.
     * </p>
     *
     * @param id  identificador único do evento a ser atualizado
     * @param dto novos dados do evento
     * @return ResponseEntity com status 200 (OK) sem corpo
     * @throws IllegalArgumentException se o ID ou DTO forem inválidos
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody EventDto dto) {
        // Converte DTO para comando de domínio
        EventCommand command = new EventCommand(dto.title(), dto.description(), dto.date());

        // Delega processamento para o handler
        updateHandler.handle(id, command);

        // Retorna resposta com status OK
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para exclusão de eventos.
     * <p>
     * Recebe o ID do evento a ser excluído e delega o processamento
     * para o handler apropriado.
     * </p>
     *
     * @param id identificador único do evento a ser excluído
     * @return ResponseEntity com status 204 (NO CONTENT)
     * @throws IllegalArgumentException se o ID for inválido
     *
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Delega processamento para o handler
        deleteHandler.handle(id);

        // Retorna resposta com status NO CONTENT
        return ResponseEntity.noContent().build();
    }
}