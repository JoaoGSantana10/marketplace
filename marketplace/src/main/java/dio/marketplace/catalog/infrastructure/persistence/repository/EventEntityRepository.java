package dio.marketplace.catalog.infrastructure.persistence.repository;

import dio.marketplace.catalog.infrastructure.persistence.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.UUID;

// recebe um event e persiste um UUID como chave primária
@RepositoryRestResource
public interface EventEntityRepository extends CrudRepository<Event, UUID> {
}
