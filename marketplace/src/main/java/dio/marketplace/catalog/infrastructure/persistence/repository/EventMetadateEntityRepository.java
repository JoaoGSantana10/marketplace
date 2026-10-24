package dio.marketplace.catalog.infrastructure.persistence.repository;

import dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.cdi.MongoRepositoryExtension;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Recebe como objeto o EventMetadata e tem como chave umas String
@RepositoryRestResource
public interface EventMetadateEntityRepository extends MongoRepository<EventMetadata, String> {
}
