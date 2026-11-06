package dio.marketplace.ticketing.infrastructure.persistence.repository;

import dio.marketplace.ticketing.infrastructure.persistence.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

/**
 * A interface CustomerCrudRepository é um repositório de persistência para a entidade Customer.
 * Ela estende a interface CrudRepository do Spring Data, fornecendo métodos CRUD (Create, Read, Update, Delete)
 * para gerenciar instâncias de Customer no banco de dados.
 *
 * A anotação @RepositoryRestResource configura o repositório para ser exposto como um recurso REST,
 * mas com a opção exported = false, o que significa que ele não será exposto publicamente como um endpoint REST.
 * O caminho do recurso é definido como "_customer".
 *
 * A interface utiliza UUID como o tipo de identificador para as entidades Customer.
 */
@RepositoryRestResource(exported = false, path = "_customer")
public interface CustomerCrudRepository extends CrudRepository<Customer, UUID> {
}
