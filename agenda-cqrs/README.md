# Agenda CQRS Backend

## Descrição
Backend simples para gerenciamento de agenda usando CQRS em Java 21 com Spring Boot e Maven. Separa comandos (escrita via JPA/H2) de queries (leitura in-memory). Eventos propagam mudanças via Spring Events.

## Instalação
1. Clone o repositório ou crie a estrutura com os arquivos fornecidos.
2. Rode: `mvn clean install`.
3. Inicie: `mvn spring-boot:run`.

## Endpoints
- POST /commands/events: Criar evento (body: {"title": "string", "description": "string", "date": "yyyy-MM-ddTHH:mm:ss"}).
- GET /queries/events: Listar eventos.
- PUT /commands/events/{id}: Atualizar evento (body similar ao create).
- DELETE /commands/events/{id}: Deletar evento.

## Testes
Rode testes unitários: `mvn test`.

## Princípios
- Separação de leitura/escrita para otimização e escalabilidade.
- Event Sourcing básico via Spring Events para sincronização.
- Injeção via Spring (@Component, etc.).

## Pontos Fortes da Arquitetura CQRS
A arquitetura CQRS oferece várias vantagens, especialmente em sistemas com cargas de leitura e escrita desbalanceadas:
- **Escalabilidade Independente**: Permite escalar o lado de leitura (queries) separadamente do lado de escrita (comandos), otimizando recursos (ex.: caches para queries).
- **Otimização de Modelos**: Modelos de dados otimizados para cada lado — consistência forte para comandos, performance para queries (ex.: denormalizado).
- **Complexidade Gerenciada**: Facilita domínios complexos com DDD, separando preocupações e permitindo integrações como Event Sourcing para auditoria e resiliência.
- **Flexibilidade**: Fácil adicionar projeções múltiplas para diferentes views de dados, sem impactar a escrita.
- **Manutenibilidade**: Código mais focado — handlers específicos para comandos e queries reduzem acoplamento.
- **Resiliência**: Em cenários distribuídos, tolera falhas melhor, com sincronização assíncrona via eventos.

## Estrutura de Pastas e Responsabilidades
A estrutura segue o padrão Maven, com ênfase na separação CQRS. Aqui está uma explicação de cada pasta principal e suas responsabilidades:

- **src/main/java/com/nexttag/agendacqrs/**: Pacote raiz da aplicação, contendo todo o código fonte principal. Responsável por organizar o código em subpacotes conforme CQRS.
    - **command/**: Lado de escrita (comandos). Contém lógica para modificar o estado.
        - **model/**: Modelos para comandos, como `EventCommand.java`. Responsável por definir dados de entrada para operações de escrita.
        - **handler/**: Handlers para comandos, como `CreateEventHandler.java`, `UpdateEventHandler.java`, `DeleteEventHandler.java`. Responsável por executar lógica de negócio, persistir mudanças e publicar eventos.
    - **query/**: Lado de leitura (queries). Contém lógica para consultar dados.
        - **model/**: Modelos para queries, como `EventQuery.java`. Responsável por definir dados otimizados para leitura (pode ser denormalizado).
        - **handler/**: Handlers para queries, como `ListEventsHandler.java`. Responsável por recuperar dados do repositório de query.
    - **domain/**: Componentes compartilhados entre command e query.
        - **event/**: Eventos de domínio, como `EventCreated.java`, `EventUpdated.java`, `EventDeleted.java`. Responsável por representar mudanças no estado para propagação (Event Sourcing básico).
    - **infrastructure/**: Infraestrutura compartilhada.
        - **repository/**: Repositórios para persistência. `CommandRepository.java` (e JPA relacionados) para escrita consistente; `QueryRepository.java` para leitura rápida (in-memory aqui). Responsável por abstrair acesso a dados.
        - **event/**: Listeners de eventos, como `QueryEventHandler.java`. Responsável por sincronizar o lado de query com eventos publicados pelos comandos, usando @EventListener.
    - **api/**: Camada de exposição externa (controllers e DTOs).
        - `CommandController.java`: Controlador para endpoints de comandos (POST/PUT/DELETE). Responsável por receber requisições de escrita e delegar para handlers.
        - `QueryController.java`: Controlador para endpoints de queries (GET). Responsável por receber requisições de leitura e delegar para handlers.
        - **dto/**: DTOs como `EventDto.java`. Responsável por transferir dados entre API e clientes, desacoplando modelos internos.
    - **AgendaCqrsApplication.java**: Classe principal da aplicação Spring Boot. Responsável por inicializar o contexto Spring e rodar a aplicação.

- **src/main/resources/**: Contém arquivos de configuração.
    - **application.properties**: Arquivo de propriedades para configurações, como banco de dados (H2), JPA e porta do servidor. Responsável por parametrizar o ambiente.

- **src/test/java/com/nexttag/agendacqrs/**: Pacote para testes, espelhando a estrutura principal.
    - **command/handler/**: Testes unitários para handlers de comandos, como `CreateEventHandlerTest.java`. Responsável por verificar lógica de escrita isoladamente, com mocks.

- **pom.xml**: Arquivo de configuração Maven na raiz. Responsável por gerenciar dependências, plugins e build.

Essa estrutura garante separação de responsabilidades, facilitando escalabilidade e manutenção em CQRS.

Esta aplicação é completa e funcional para desenvolvimento. Para produção, use um bus de eventos real (ex.: Kafka) e persistência para queries (ex.: Redis).