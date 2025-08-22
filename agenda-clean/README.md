# Agenda Clean Architecture Backend

## Descrição
Backend simples para gerenciamento de agenda usando Clean Architecture em Java 21 com Spring Boot e Maven. Camadas concêntricas: Entity > UseCase > InterfaceAdapter > Framework, com dependências de fora para dentro.

## Instalação
1. Clone o repositório ou crie a estrutura com os arquivos fornecidos.
2. Rode: `mvn clean install`.
3. Inicie: `mvn spring-boot:run`.

## Endpoints
- POST /events: Criar evento (body: {"title": "string", "description": "string", "date": "yyyy-MM-ddTHH:mm:ss"}).
- GET /events: Listar eventos.
- PUT /events/{id}: Atualizar evento (body similar ao create).
- DELETE /events/{id}: Deletar evento.

## Testes
Rode testes unitários: `mvn test`.

## Princípios
- Dependências de fora para dentro.
- Núcleo (entity/usecase) independente de frameworks.
- Injeção via Spring (@Component, @Service, etc.).

## Pontos Fortes da Arquitetura Clean
A Clean Architecture oferece várias vantagens, especialmente em projetos enterprise e longevos:
- **Independência de Frameworks**: O núcleo (entidades e usecases) não depende de tecnologias externas, permitindo trocas sem refatoração massiva.
- **Testabilidade**: Camadas internas podem ser testadas isoladamente, sem mocks complexos para infra, facilitando TDD e BDD.
- **Manutenibilidade**: Regras claras de dependência (fluxo inward) mantêm o código organizado, com foco no negócio, reduzindo dívida técnica.
- **Flexibilidade**: Fácil adicionar ou mudar drivers (ex.: de REST para CLI) ou gateways (ex.: de SQL para NoSQL), suportando evolução.
- **Escalabilidade**: Separação permite otimizar camadas independentemente, alinhada a DDD para domínios complexos.
- **Foco no Negócio**: Entidades e usecases representam regras puras, isoladas de detalhes técnicos, melhorando alinhamento com stakeholders.

## Estrutura de Pastas e Responsabilidades
A estrutura segue o padrão Maven, com camadas da Clean Architecture. Aqui está uma explicação de cada pasta principal e suas responsabilidades:

- **src/main/java/com/nexttag/agendaclean/**: Pacote raiz da aplicação, contendo todo o código fonte principal. Responsável por organizar o código em camadas concêntricas.
    - **entity/**: Camada mais interna (núcleo). Contém entidades como `Event.java`. Responsável por definir objetos de negócio imutáveis e regras básicas.
    - **usecase/**: Camada de regras de negócio. Contém interfaces e implementações como `EventUseCase.java` e `EventUseCaseImpl.java`. Responsável por orquestrar lógica de aplicação, dependendo apenas de entidades e gateways.
    - **interfaceadapter/**: Camada de adaptação de interfaces. Converte dados entre usecases e frameworks.
        - **gateway/**: Interfaces para repositórios, como `EventRepositoryGateway.java`. Responsável por definir contratos para persistência, invertendo dependências.
    - **framework/**: Camada externa (drivers e adapters). Lida com detalhes técnicos.
        - **adapter/**: Implementações específicas.
            - **persistence/**: Adaptadores para DB, como `EventEntity.java` (JPA), `EventJpaRepository.java` (Spring Data), `EventRepositoryImpl.java` (impl do gateway). Responsável por mapear entidades para armazenamento.
            - **web/**: Adaptadores para HTTP, como `EventController.java` (Spring RestController) e `dto/EventDto.java` (DTOs). Responsável por expor endpoints e mapear para usecases.
    - **AgendaCleanApplication.java**: Classe principal Spring Boot. Responsável por inicializar o contexto e rodar a app.

- **src/main/resources/**: Contém configurações.
    - **application.properties**: Propriedades para DB (H2), JPA e servidor. Responsável por parametrizar o ambiente.

- **src/test/java/com/nexttag/agendaclean/**: Pacote para testes, espelhando a estrutura.
    - **usecase/**: Testes unitários para usecases, como `EventUseCaseImplTest.java`. Responsável por verificar lógica de negócio com mocks.

- **pom.xml**: Configuração Maven na raiz. Gerencia dependências e build.

Essa estrutura garante independência das camadas internas, facilitando manutenção e evolução.

Esta aplicação é completa e funcional para desenvolvimento. Para produção, adicione segurança, validações e DB real.