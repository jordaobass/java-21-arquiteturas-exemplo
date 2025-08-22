# Comparação de Arquiteturas para Backend de Agenda

## Visão Geral
Este repositório (ou exemplos) compara três abordagens para um backend de agenda simples (gerenciamento de eventos) em Java 21 com Spring Boot e Maven:
1. **Arquitetura Hexagonal**: Foca em portas e adaptadores para isolar o domínio de infraestruturas. Ideal para testabilidade e troca de tecnologias.
2. **Clean Architecture**: Camadas concêntricas com dependências invertidas. Similar à hexagonal, mas mais explícita em círculos (entity > usecase > interfaceadapter > framework). Boa para manutenção em apps enterprise.
3. **CQRS**: Padrão que separa comandos (escrita, consistência) de queries (leitura, performance). Não é uma arquitetura full, mas um complemento; aqui integrado com Event Sourcing via Spring Events. Útil para apps com alta leitura vs. escrita, escalabilidade distribuída.

## Diferenças Principais
| Aspecto              | Hexagonal                          | Clean Architecture                 | CQRS                               |
|----------------------|------------------------------------|------------------------------------|------------------------------------|
| **Foco Principal**  | Desacoplamento via portas/adaptadores | Camadas concêntricas e independência | Separação leitura/escrita          |
| **Estrutura**       | Domain (entity/port/usecase), Adapter (in/out) | Entity/UseCase/InterfaceAdapter/Framework | Command/Query/Domain/Infrastructure |
| **Complexidade**    | Média; bom para monolitos          | Média; evoluída da hexagonal       | Alta; para apps complexos/escaláveis |
| **Vantagens**       | Fácil testar domínio isolado       | Regras de dependência claras       | Otimização independente de lados   |
| **Desvantagens**    | Pode ser overkill para simples     | Similar, com mais camadas          | Adiciona overhead (sincronização)  |
| **Quando Usar**     | Apps que mudam infra frequentemente | Projetos longevos com DDD          | Alta carga de queries, microsserviços |

Todas usam Spring Boot para injeção e web, H2 para DB. Em produção, use DI com @Bean/@Component e bancos reais. Para mais, estude os códigos!