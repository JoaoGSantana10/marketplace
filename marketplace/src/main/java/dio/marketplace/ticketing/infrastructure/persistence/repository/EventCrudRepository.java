package dio.marketplace.ticketing.infrastructure.persistence.repository;

import dio.marketplace.ticketing.infrastructure.persistence.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * A interface EventCrudRepository é um repositório de CRUD (Create, Read, Update, Delete) para a entidade Event.
 * Ela estende a interface CrudRepository fornecida pelo Spring Data, permitindo operações básicas de persistência
 * no banco de dados para a entidade Event.
 *
 * Anotações:
 * @RepositoryRestResource: Indica que este repositório será exposto como um recurso REST. O parâmetro 'exported = false'
 * significa que este repositório não será exposto publicamente como um endpoint REST. O parâmetro 'path = "_event"'
 * define o caminho base para acessar os recursos deste repositório.
 *
 * A interface define métodos para salvar, buscar, atualizar e deletar eventos no banco de dados, utilizando o tipo UUID
 * como identificador único para cada evento.
 */
import java.util.UUID;
/**
 * Interface EventCrudRepository do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@RepositoryRestResource(exported = false, path = "_event")
public interface EventCrudRepository extends CrudRepository<Event, UUID> {
    boolean existsByCorrelationIdAndSectors_Seats_CorrelationId(UUID eventId, String seatId);
}
