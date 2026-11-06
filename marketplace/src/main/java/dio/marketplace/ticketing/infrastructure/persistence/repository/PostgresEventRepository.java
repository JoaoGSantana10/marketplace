package dio.marketplace.ticketing.infrastructure.persistence.repository;

import dio.marketplace.ticketing.domain.Event;
import dio.marketplace.ticketing.domain.EventRepository;
import dio.marketplace.ticketing.domain.Seat;
import dio.marketplace.ticketing.domain.Sector;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A classe PostgresEventRepository é uma implementação do repositório de eventos (EventRepository) que utiliza o
 * Spring Data JPA para persistir eventos em um banco de dados PostgreSQL.
 *
 * Anotações:
 * @Repository: Indica que esta classe é um componente de repositório do Spring, permitindo a injeção de dependências
 * e a detecção automática pelo Spring.
 *
 * Atributos:
 * - eventCrudRepository: Uma instância do EventCrudRepository, que é um repositório CRUD para a entidade Event.
 *   Ele é usado para realizar operações básicas de persistência no banco de dados.
 *
 * Construtor:
 * - PostgresEventRepository(EventCrudRepository eventCrudRepository): Construtor que recebe uma instância do
 *   EventCrudRepository como parâmetro e a atribui ao atributo eventCrudRepository.
 *
 * Métodos:
 * - save(Event event): Método que salva um evento no banco de dados. Ele realiza as seguintes etapas:
 *   1. Mapeia os setores e assentos do evento para suas respectivas entidades de persistência.
 *   2. Cria uma instância da entidade Event com os dados mapeados.
 *   3. Chama o método save do eventCrudRepository para persistir a entidade Event no banco de dados.
 *
 * O método save utiliza streams para transformar os setores e assentos do domínio em suas representações
 * correspondentes na camada de persistência, garantindo que os dados sejam corretamente armazenados no banco.
 */
@Repository
public class PostgresEventRepository implements EventRepository {
    private final EventCrudRepository eventCrudRepository;

    public PostgresEventRepository(EventCrudRepository eventCrudRepository) {
        this.eventCrudRepository = eventCrudRepository;
    }

    @Override
    public void save(Event event){
        var sectors = event.getSeats().entrySet().stream()
                .map(entry -> {
                    Sector domainSector = entry.getKey();
                    List<Seat> domainSeats = entry.getValue();

                    var seats = domainSeats.stream()
                            .map(s -> new dio.marketplace.ticketing.infrastructure.persistence.entity.Seat(
                                    s.getId(),
                                    s.getCorrelationId().id()
                            ))
                            .toList();

                    return new dio.marketplace.ticketing.infrastructure.persistence.entity.Sector(
                            domainSector.getId(),
                            domainSector.getCorrelationId().id(),
                            domainSector.getPrice(),
                            seats
                    );
                })
                .toList();

        var eventEntity = new dio.marketplace.ticketing.infrastructure.persistence.entity.Event(
                event.getId(),
                event.getCorrelationId().id(),
                sectors
        );

        eventCrudRepository.save(eventEntity);
    }
}
