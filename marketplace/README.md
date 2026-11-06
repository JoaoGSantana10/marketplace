# Marketplace

Aplicação Spring Boot para um marketplace de eventos. O código é organizado por **contextos delimitados**: cadastro (`registration`), catálogo (`catalog`) e venda/reserva de ingressos (`ticketing`). Cada contexto mantém seu domínio, casos de uso e adaptadores de infraestrutura; eventos de aplicação fazem a integração entre eles.

## Visão geral da arquitetura

O projeto segue uma arquitetura modular inspirada em ports and adapters/arquitetura hexagonal:

- **Domínio**: entidades, identificadores, records, contratos de repositório e exceções de negócio. Não depende dos bancos.
- **Aplicação**: casos de uso e transformação de dados; coordena o domínio e define os fluxos de negócio.
- **Infraestrutura**: controladores HTTP, listeners de eventos, configurações Spring, entidades de persistência e implementações dos repositórios.
- **Eventos**: `CustomerCreated` e `EventUpdated` são DTOs compartilhados. O cadastro publica eventos; o ticketing os consome para manter sua própria projeção.

O ponto de entrada é `dio.marketplace.MarketplaceApplication`, que inicializa o Spring Boot, habilita o agendamento assíncrono, cache e a configuração dos módulos.

## Módulos e classes

### `dio.marketplace.registration` — cadastro

- `domain.Customer`: modelo de cliente do domínio.
- `domain.CustomerId`: identificador UUID validado.
- `domain.CustomerRepository`: porta de persistência de clientes.
- `infrastructure.persistence.entity.Customer`: entidade JPA com nome, e-mail, data de criação e endereço; valida e-mail/não vazio e gera UUID antes da persistência.
- `infrastructure.persistence.entity.Address`: endereço persistido como parte do cliente.
- `infrastructure.persistence.entity.projection.CustomerExcerpt`: projeção resumida exposta pelo Spring Data REST.
- `infrastructure.persistence.repository.CustomerEntityRepository`: repositório Spring Data REST/JPA (paginação, ordenação e CRUD).
- `infrastructure.persistence.repository.JpaCustomerRepository`: adaptador entre o domínio e a entidade JPA.
- `infrastructure.event.CustomerEventHandler`: publica eventos após criação, atualização e remoção do cliente.
- `infrastructure.ResgistrationConfiguration`: configura o `DataSource`, JPA e transações do banco de registration (o nome da classe mantém a grafia existente por compatibilidade).

### `dio.marketplace.catalog` — catálogo e vitrine

- `domain.Event`, `EventId`: evento exibido na vitrine e seu identificador.
- `domain.EventMetadata`: descrição, requisitos técnicos e assentos agrupados por setor.
- `domain.Sector`, `SectorId`, `Seat`, `SeatId`: modelo de setores, preços e assentos do catálogo.
- `domain.EventRepository` e `EventMetadataRepository`: portas para buscar eventos e metadados.
- `application.BrowseShowcaseUseCase`: busca eventos, enriquece-os e converte-os para `EventOutput`; usa cache Redis quando o resultado não está vazio.
- `application.EventEnricher`: consulta metadados de forma assíncrona (`@Async`).
- `application.dto.EventOutput`: DTO serializável da vitrine, incluindo `EventMetadataOutput` e `SeatOutput`.
- `infrastructure.http.ShowcaseController`: endpoint `GET /showcase`.
- `infrastructure.persistence.entity.Event` e `EventMetadata`: entidades JPA/MongoDB.
- `infrastructure.persistence.repository.JpaEventRepository`: adaptador JPA para eventos.
- `infrastructure.persistence.repository.MongoEventMetadataRepository`: adaptador MongoDB para metadados e mapeamento de setores/assentos.
- `infrastructure.event.EventListener`: reage a criação, atualização e remoção de eventos persistidos.
- `infrastructure.event.EventMetadataEventListener`: traduz eventos do MongoDB em eventos da aplicação.
- `CatalogConfiguration`: configura datasource/JPA do catálogo e Redis usado pelo cache.

### `dio.marketplace.ticketing` — reserva de assentos

- `domain.Customer`, `CustomerId`: cliente projetado para o contexto de ingressos.
- `domain.Event`, `EventId`: evento e identificador da projeção de ticketing.
- `domain.Sector`, `SectorId`, `Seat`, `SeatId`: setores, preços e assentos da projeção.
- `domain.CustomerRepository` e `EventRepository`: portas para salvar clientes/eventos, consultar assentos e tentar bloqueios.
- `domain.SeatAlreadyReservedException`: informa tentativa de reservar assento já bloqueado.
- `domain.SeatNotFoundException`: informa assento inexistente no evento informado.
- `application.CreateCustomerUseCase` e `CreateEventUseCase`: consomem eventos compartilhados e criam projeções locais.
- `application.SelectSeatUseCase`: valida evento/assento e tenta o bloqueio para o cliente.
- `infrastructure.http.SeatSelectionController`: `POST /ticketing/events/{eventId}/seats/select`, recebendo o assento no corpo e o cliente no cabeçalho `X-CUSTOMER-ID`; retorna HTTP 201 quando o bloqueio é criado.
- `infrastructure.http.request.SeatSelectionRequest`: request record convertido em `SeatId`.
- `infrastructure.event.TicketingEventListener`: encaminha `CustomerCreated` e `EventUpdated` aos casos de uso.
- `infrastructure.persistence.entity.Event`, `Sector`, `Seat`, `Customer`: projeções JPA do ticketing.
- `infrastructure.persistence.entity.SeatLock`: registro Redis com TTL de 30 segundos, indexado por cliente.
- `infrastructure.persistence.repository.EventCrudRepository`, `CustomerCrudRepository`: repositórios Spring Data JPA.
- `infrastructure.persistence.repository.WorkOfUnitEventRepository`: adaptador que salva eventos, consulta assentos e usa Redis para bloqueio; o nome atual substitui o antigo `PostgresEventRepository`.
- `infrastructure.persistence.repository.PostgresCustomerRepository`: adaptador de clientes para PostgreSQL.
- `infrastructure.persistence.repository.RedisSeatLockRepository`: repositório Redis de bloqueios.
- `TicketingConfiguration`: configura datasource/JPA PostgreSQL e Redis do bloqueio.

### Tipos compartilhados

`dio.marketplace.common.infrastructure.event.dto.CustomerCreated` representa cliente criado (`id`, `name`). `EventUpdated` representa evento atualizado e contém os records aninhados `Sector` e `Seat`, usados para transportar preço e assentos entre módulos.

## Persistência e infraestrutura

| Contexto | Tecnologia | Banco/porta padrão | Uso |
|---|---|---|---|
| Registration | Spring Data JPA | MySQL `localhost:3307`, schema `registration` | Clientes e endereços |
| Catalog | Spring Data JPA | MySQL `localhost:3308`, schema `catalog` | Eventos e setores |
| Catalog metadata | Spring Data MongoDB | MongoDB `localhost:27018` | Descrição, requisitos e mapa de assentos |
| Catalog cache | Spring Cache + Redis/Jedis | Redis `localhost:6380` | Cache da vitrine |
| Ticketing | Spring Data JPA | PostgreSQL `localhost:5433`, database `ticketing` | Projeção de eventos, setores e assentos |
| Ticketing locking | Spring Data Redis/Jedis | Redis `localhost:6381` | Bloqueios temporários de assentos |

As configurações de desenvolvimento estão em `src/main/resources/application.properties`. As credenciais de desenvolvimento correspondem às definidas no `compose.yml`; não coloque credenciais reais nesse arquivo. Os valores de `hibernate.hbm2ddl.auto` são `update` para registration/catalog e `create` para ticketing, portanto ticketing recria o esquema ao iniciar.

## Tecnologias

- Java 25 (toolchain Gradle), Spring Boot 4.1.1 e Gradle Wrapper.
- Spring Web, Validation, Actuator, Data JPA, Data REST, MongoDB e Redis.
- Hibernate, MySQL Connector/J, PostgreSQL JDBC, Jedis.
- Lombok para getters, setters, construtores e métodos de entidades.
- JUnit Platform/Spring Boot Test para testes.
- Docker Compose para os serviços locais.

## Execução local

Pré-requisitos: JDK 25, Docker com Compose e portas livres `3307`, `3308`, `27018`, `6380`, `5433` e `6381`.

```bash
./gradlew test
./gradlew bootRun
```

O suporte `spring-boot-docker-compose` inicia os serviços quando a aplicação sobe (`spring.docker.compose.lifecycle-management=start_only`). Para iniciar a infraestrutura manualmente:

```bash
docker compose up -d
./gradlew bootRun
```

A saúde pode ser consultada em `GET /actuator/health`. Para parar os serviços, use `docker compose down`; para remover também os dados persistidos, use `docker compose down -v`.

## Fluxos principais

1. O cadastro persiste um cliente e publica `CustomerCreated`; o ticketing cria sua projeção.
2. Um evento atualizado gera `EventUpdated`; o ticketing cria/atualiza a projeção de setores e assentos.
3. `GET /showcase` consulta eventos JPA, busca metadados MongoDB em paralelo e devolve uma vitrine enriquecida, cacheada no Redis do catálogo.
4. `POST /ticketing/events/{eventId}/seats/select` valida o assento e grava um `SeatLock` no Redis por 30 segundos. Se o assento não existir ou já estiver bloqueado, o domínio lança a exceção correspondente.

## Documentação do código

Todas as classes, interfaces, records, enums, exceções, construtores, métodos e atributos relevantes de `src/main/java` possuem Javadocs em português. As anotações Spring, JPA, MongoDB, Redis, Lombok e validação efetivamente usadas são explicadas junto das declarações que elas configuram. Os Javadocs descrevem contratos, parâmetros, retornos, exceções, eventos e mapeamentos; comentários existentes corretos foram preservados.

## Observações

- O endpoint de clientes é exposto pelo Spring Data REST conforme `CustomerEntityRepository`; seus recursos seguem a convenção do Spring Data REST e a projeção `excerpt`.
- A seleção de assento depende do cabeçalho `X-CUSTOMER-ID` conter um UUID válido.
- O cache e o locking são Redis distintos para evitar que bloqueios de venda concorram com o cache da vitrine.
- O projeto mantém arquivos gerados e configurações da IDE fora desta documentação.
