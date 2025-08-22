# Agenda Layered Backend

## Descrição
Backend simples para gerenciamento de agenda usando Arquitetura em Camadas em Java 21 com Spring Boot e Maven. Camadas: Model (entidade), Repository (dados), Service (negócio), Controller (apresentação).

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
- Fluxo linear: Controllers chamam services, que chamam repositories.
- Simplicidade: Menos abstrações, ideal para apps básicos.
- Injeção via Spring (@Service, @Repository, etc.).

## Pontos Fortes da Arquitetura em Camadas
A arquitetura em camadas é simples e amplamente usada, com vantagens para projetos iniciais:
- **Facilidade de Implementação**: Estrutura linear reduz boilerplate, permitindo desenvolvimento rápido.
- **Organização Clara**: Camadas separadas (apresentação, negócio, dados) facilitam compreensão e manutenção.
- **Integração com Frameworks**: Nativa no Spring Boot, com anotações prontas para cada camada.
- **Escalabilidade Inicial**: Boa para monolitos; fácil adicionar camadas se necessário.
- **Custo Baixo**: Menos complexa que hexagonal/clean, evitando overengineering em apps simples.
- **Testabilidade**: Fácil mockar camadas inferiores para testes unitários.

## Estrutura de Pastas e Responsabilidades
A estrutura segue o padrão Maven, com camadas explícitas. Aqui está uma explicação de cada pasta principal e suas responsabilidades:

- **src/main/java/com/nexttag/agendalayered/**: Pacote raiz da aplicação, contendo todo o código fonte principal. Responsável por organizar o código em camadas.
    - **controller/**: Camada de apresentação. Contém `EventController.java`. Responsável por lidar com requisições HTTP, mapear endpoints e delegar para o service.
    - **service/**: Camada de negócio. Contém `EventService.java`. Responsável por orquestrar lógica de aplicação, como validações e chamadas ao repository.
    - **repository/**: Camada de dados. Contém `EventRepository.java`. Responsável por abstrair acesso ao banco, usando Spring Data JPA.
    - **model/**: Camada de modelo. Contém `Event.java`. Responsável por definir a entidade de domínio, que também serve como mapeamento JPA.
    - **AgendaLayeredApplication.java**: Classe principal Spring Boot. Responsável por inicializar o contexto e rodar a aplicação.

- **src/main/resources/**: Contém arquivos de configuração.
    - **application.properties**: Propriedades para banco (H2), JPA e servidor. Responsável por parametrizar o ambiente.

- **src/test/java/com/nexttag/agendalayered/**: Pacote para testes, espelhando a estrutura principal.
    - **service/**: Testes unitários para o service, como `EventServiceTest.java`. Responsável por verificar lógica de negócio com mocks.

- **pom.xml**: Arquivo de configuração Maven na raiz. Responsável por gerenciar dependências, plugins e build.

Essa estrutura é direta e facilita o fluxo de dependências de cima para baixo.

## Documentação das Classes
Todas as classes foram documentadas com Javadoc para fins educacionais. Os comentários explicam o papel de cada componente na arquitetura em camadas, responsabilidades e métodos. Consulte o código fonte para detalhes.

Esta aplicação é completa e funcional para um ambiente de desenvolvimento. Para produção, adicione segurança, validações e um banco real.