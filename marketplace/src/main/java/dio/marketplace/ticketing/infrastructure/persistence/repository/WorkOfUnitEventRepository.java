package dio.marketplace.ticketing.infrastructure.persistence.repository;

import dio.marketplace.ticketing.domain.*;
import dio.marketplace.ticketing.infrastructure.persistence.entity.SeatLock;
import org.springframework.stereotype.Repository;

import java.time.Instant;
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
public class WorkOfUnitEventRepository implements EventRepository {
/**
 * Armazena o atributo eventCrudRepository e o estado necessário ao componente.
 */
    private final EventCrudRepository eventCrudRepository;
/**
 * Armazena o atributo redisSeatLockRepository e o estado necessário ao componente.
 */
    private final RedisSeatLockRepository redisSeatLockRepository;

/**
 * Executa a operação WorkOfUnitEventRepository, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventCrudRepository parâmetro utilizado pela operação.
 * @param redisSeatLockRepository parâmetro utilizado pela operação.
 */
    public WorkOfUnitEventRepository(EventCrudRepository eventCrudRepository, RedisSeatLockRepository redisSeatLockRepository) {
        this.eventCrudRepository = eventCrudRepository;
        this.redisSeatLockRepository = redisSeatLockRepository;
    }

/**
 * Executa a operação save, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
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

/**
 * Executa a operação existsSeat, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventId parâmetro utilizado pela operação.
 * @param seatId parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Override
    public boolean existsSeat(EventId eventId, SeatId seatId) {
        return eventCrudRepository.existsByCorrelationIdAndSectors_Seats_CorrelationId(eventId.id(), seatId.id());
    }

/**
 * Executa a operação tryLockSeat, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventId parâmetro utilizado pela operação.
 * @param seatId parâmetro utilizado pela operação.
 * @param customerId parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Override
    public boolean tryLockSeat(EventId eventId, SeatId seatId, CustomerId customerId) {
        String lockId = eventId.id() + ":" + seatId.id();

        if (redisSeatLockRepository.existsById(lockId)) {
            return false;
        }

        var lock = new SeatLock(lockId, customerId.id().toString(), Instant.now());
        redisSeatLockRepository.save(lock);
        return true;
    }
}
