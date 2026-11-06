package dio.marketplace.ticketing.infrastructure.persistence.repository;

import dio.marketplace.ticketing.domain.Customer;
import dio.marketplace.ticketing.domain.CustomerRepository;
import org.springframework.stereotype.Repository;

/**
 * A classe PostgresCustomerRepository é uma implementação do repositório de clientes (CustomerRepository)
 * que utiliza o PostgreSQL como banco de dados para persistir informações de clientes.
 *
 * Ela é anotada com @Repository, indicando que é um componente Spring responsável por operações de persistência.
 *
 * A classe possui um construtor que recebe uma instância de CustomerCrudRepository, que é uma interface
 * que estende JpaRepository ou CrudRepository do Spring Data, permitindo operações CRUD no banco de dados.
 *
 * O método save(Customer customer) é responsável por salvar um objeto Customer no banco de dados.
 * Ele cria uma entidade Customer a partir do objeto Customer fornecido, mapeando seus campos para os campos da entidade,
 * e então chama o método save() do customerCrudRepository para persistir a entidade no banco de dados.
 *
 * Em resumo, esta classe atua como uma ponte entre a camada de domínio (Customer) e a camada de persistência (banco de dados),
 * garantindo que os dados dos clientes sejam armazenados corretamente no PostgreSQL.
 */
@Repository
public class PostgresCustomerRepository implements CustomerRepository {
/**
 * Armazena o atributo customerCrudRepository e o estado necessário ao componente.
 */
    private final CustomerCrudRepository customerCrudRepository;

/**
 * Executa a operação PostgresCustomerRepository, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customerCrudRepository parâmetro utilizado pela operação.
 */
    public PostgresCustomerRepository(CustomerCrudRepository customerCrudRepository){
        this.customerCrudRepository = customerCrudRepository;
    }

/**
 * Executa a operação save, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customer parâmetro utilizado pela operação.
 */
    @Override
    public void save(Customer customer) {
        var entity = new dio.marketplace.ticketing.infrastructure.persistence.entity.Customer(
                customer.getId(),
                customer.getCorrelationId().id(),
                customer.getName()
        );
        customerCrudRepository.save(entity);
    }
}
