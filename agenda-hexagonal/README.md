# Agenda Hexagonal Backend

## Descrição
Backend simples para gerenciamento de agenda usando Arquitetura Hexagonal em Java 21 com Spring Boot e Maven. O domínio está isolado, com portas para repositórios e adaptadores para HTTP (Spring Web) e persistência (H2 in-memory via JPA).

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
- Domínio central: Entidades e usecases independentes.
- Adaptadores: Isolam tecnologias externas. Injeção via Spring (@Component, @Service, etc.).
- Para rodar testes, use JUnit e Mockito (incluídos).

## Pontos Fortes da Arquitetura Hexagonal
A arquitetura hexagonal (também conhecida como Ports and Adapters) oferece várias vantagens, especialmente em aplicações complexas e evolutivas:
- **Desacoplamento**: O núcleo do domínio (lógica de negócio) é independente de tecnologias externas, como frameworks, bancos de dados ou APIs, permitindo mudanças sem impacto no código principal.
- **Testabilidade**: Facilita testes unitários e de integração, pois o domínio pode ser testado isoladamente, usando mocks para as portas, sem depender de infraestrutura real.
- **Flexibilidade e Adaptabilidade**: É fácil trocar adaptadores (ex.: mudar de H2 para PostgreSQL ou de REST para GraphQL) sem alterar o domínio, promovendo a evolução do sistema.
- **Manutenibilidade**: Separação clara de responsabilidades (domínio vs. infraestrutura) torna o código mais organizado, legível e fácil de manter a longo prazo.
- **Escalabilidade**: Suporta crescimento, como adicionar novos adaptadores para microsserviços ou integrações, mantendo o foco na lógica de negócio.
- **Foco no Negócio**: Enfatiza o domínio como centro, alinhando o software às regras de negócio reais, o que é ideal para projetos com Domain-Driven Design (DDD).

## Estrutura de Pastas e Responsabilidades
A estrutura segue o padrão Maven para projetos Java, com ênfase na separação hexagonal. Aqui está uma explicação de cada pasta principal e suas responsabilidades:

- **src/main/java/com/nexttag/agendahexagonal/**: Pacote raiz da aplicação, contendo todo o código fonte principal. Responsável por organizar o código em subpacotes conforme a arquitetura.
    - **domain/**: Representa o núcleo do domínio (hexágono central). Contém a lógica de negócio pura, independente de tecnologias externas.
        - **entity/**: Armazena as entidades do domínio, como `Event.java`. Responsável por definir os objetos de negócio imutáveis (usando records no Java 21) que representam os dados e comportamentos essenciais.
        - **port/**: Define as interfaces (portas) para comunicação com o mundo externo.
            - **in/**: Portas de entrada, como `EventUseCase.java`. Responsável por expor os serviços/usecases que podem ser chamados por adaptadores de entrada (ex.: controllers).
            - **out/**: Portas de saída, como `EventRepositoryPort.java`. Responsável por definir contratos para persistência ou outras saídas, sem implementação concreta.
        - **usecase/**: Contém as implementações dos casos de uso, como `EventUseCaseImpl.java`. Responsável por orquestrar a lógica de negócio, usando as portas para interagir com repositórios ou outros componentes, mantendo o domínio isolado.
    - **adapter/**: Contém os adaptadores que conectam o domínio ao mundo externo, implementando as portas.
        - **in/**: Adaptadores de entrada, que recebem requisições externas e as traduzem para chamadas ao domínio.
            - **web/**: Adaptadores para interface web/HTTP, como `EventController.java` (controlador Spring) e `dto/EventDto.java` (DTOs para transferência de dados). Responsável por mapear requisições HTTP para usecases do domínio e retornar respostas.
        - **out/**: Adaptadores de saída, que implementam as portas de saída para infraestrutura específica.
            - **persistence/**: Adaptadores para persistência, como `EventEntity.java` (entidade JPA), `EventJpaRepository.java` (repositório Spring Data JPA) e `EventRepositoryAdapter.java` (implementação da porta). Responsável por mapear entidades de domínio para o banco de dados e executar operações de CRUD usando tecnologias como JPA e H2.
    - **AgendaHexagonalApplication.java**: Classe principal da aplicação Spring Boot. Responsável por inicializar o contexto Spring e rodar a aplicação.

- **src/main/resources/**: Contém arquivos de configuração e recursos estáticos.
    - **application.properties**: Arquivo de propriedades para configurações da aplicação, como conexão com o banco de dados (H2 in-memory), configurações JPA e porta do servidor. Responsável por parametrizar o ambiente de execução sem alterar o código.

- **src/test/java/com/nexttag/agendahexagonal/**: Pacote para testes, espelhando a estrutura principal.
    - **domain/usecase/**: Contém testes unitários para os usecases, como `EventUseCaseImplTest.java`. Responsável por verificar a lógica de negócio isoladamente, usando mocks para dependências (ex.: repositórios).

- **pom.xml**: Arquivo de configuração Maven na raiz. Responsável por gerenciar dependências, plugins, versão do Java e build da aplicação.

- **README.md**: Este arquivo de documentação. Responsável por fornecer instruções de uso, explicações e guias para desenvolvedores.

Essa estrutura garante o isolamento do domínio, facilitando a manutenção e a evolução, alinhada aos princípios da arquitetura hexagonal.

Esta aplicação é completa e funcional para um ambiente de desenvolvimento. Para produção, adicione segurança, validações e um banco real.



```aiignore
agenda-hexagonal/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── agenda/
│   │   │               ├── domain/
│   │   │               │   ├── entity/
│   │   │               │   │   └── Event.java  # Entidade do domínio (Evento)
│   │   │               │   ├── port/
│   │   │               │   │   ├── in/
│   │   │               │   │   │   └── EventUseCase.java  # Porta de entrada para usecases
│   │   │               │   │   └── out/
│   │   │               │   │       └── EventRepositoryPort.java  # Porta para repositório (interface)
│   │   │               │   └── usecase/
│   │   │               │       ├── CreateEventUseCase.java
│   │   │               │       ├── ListEventsUseCase.java
│   │   │               │       ├── UpdateEventUseCase.java
│   │   │               │       └── DeleteEventUseCase.java
│   │   │               ├── adapter/
│   │   │               │   ├── in/
│   │   │               │   │   └── web/
│   │   │               │   │       ├── EventController.java  # Adaptador HTTP (Spring RestController)
│   │   │               │   │       └── dto/
│   │   │               │   │           └── EventDto.java  # DTO para entrada/saída
│   │   │               │   └── out/
│   │   │               │       └── persistence/
│   │   │               │           ├── EventEntity.java  # Entidade JPA
│   │   │               │           ├── EventJpaRepository.java  # Repositório Spring Data JPA
│   │   │               │           └── EventRepositoryAdapter.java  # Implementação da porta com JPA
│   │   │               └── AgendaApplication.java  # Classe principal Spring Boot
│   │   └── resources/
│   │       └── application.yml  # Configurações (H2 in-memory)
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── agenda/
│                       ├── domain/
│                       │   └── usecase/
│                       │       └── CreateEventUseCaseTest.java  # Testes unitários
│                       └── adapter/
│                           └── in/
│                               └── web/
│                                   └── EventControllerTest.java  # Testes de integração
├── pom.xml  # Configuração Maven
└── README.md  # Documentação
```