# Comparação de Arquiteturas para Backend de Agenda

## Visão Geral
Este repositório (ou exemplos) compara quatro abordagens para um backend de agenda simples (gerenciamento de eventos: criar, listar, atualizar, deletar). Cada uma usa Java 21 com Spring Boot e Maven para consistência:
1. **Arquitetura Hexagonal**: Foca em portas e adaptadores para isolar o domínio de infraestruturas. Ideal para testabilidade e troca de tecnologias.
2. **Clean Architecture**: Camadas concêntricas com dependências invertidas. Similar à hexagonal, mas mais explícita em círculos (entity > usecase > interfaceadapter > framework). Boa para manutenção em apps enterprise.
3. **CQRS**: Padrão que separa comandos (escrita, consistência) de queries (leitura, performance). Não é uma arquitetura full, mas um complemento; aqui integrado com Event Sourcing via Spring Events. Útil para apps com alta leitura vs. escrita, escalabilidade distribuída.
4. **Arquitetura em Camadas (Layered)**: Estrutura linear simples (controller > service > repository > model). Ideal para projetos iniciais ou MVPs, com fluxo direto e menos abstrações. Fácil de implementar, mas menos flexível para mudanças complexas.

## Diferenças Principais
| Aspecto              | Hexagonal                          | Clean Architecture                 | CQRS                               | Layered (em Camadas)               |
|----------------------|------------------------------------|------------------------------------|------------------------------------|------------------------------------|
| **Foco Principal**  | Desacoplamento via portas/adaptadores | Camadas concêntricas e independência | Separação leitura/escrita          | Fluxo linear simples               |
| **Estrutura**       | Domain (entity/port/usecase), Adapter (in/out) | Entity/UseCase/InterfaceAdapter/Framework | Command/Query/Domain/Infrastructure | Controller/Service/Repository/Model |
| **Complexidade**    | Média; bom para monolitos          | Média; evoluída da hexagonal       | Alta; para apps complexos/escaláveis | Baixa; para apps básicos           |
| **Vantagens**       | Fácil testar domínio isolado       | Regras de dependência claras       | Otimização independente de lados   | Rápida implementação e manutenção simples |
| **Desvantagens**    | Pode ser overkill para simples     | Similar, com mais camadas          | Adiciona overhead (sincronização)  | Menos flexível para grandes mudanças |
| **Quando Usar**     | Apps que mudam infra frequentemente | Projetos longevos com DDD          | Alta carga de queries, microsserviços | MVPs, protótipos ou apps CRUD básicos |

Todas usam Spring Boot para injeção e web, H2 para DB. Em produção, injete dependências com @Bean/@Component e use bancos reais. Para mais, estude os códigos de cada pasta/projeto!