package dio.marketplace.ticketing.infrastructure.persistence.repository;

import dio.marketplace.ticketing.infrastructure.persistence.entity.SeatLock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * Interface RedisSeatLockRepository do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@RepositoryRestResource(exported = false)
public interface RedisSeatLockRepository extends CrudRepository<SeatLock, String> {
    Optional<SeatLock> findByCustomerId(String customerId);
}
