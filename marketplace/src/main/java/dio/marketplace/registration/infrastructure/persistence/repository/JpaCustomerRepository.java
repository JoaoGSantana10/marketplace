package dio.marketplace.registration.infrastructure.persistence.repository;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.registration.domain.Customer;
import dio.marketplace.registration.domain.CustomerId;
import dio.marketplace.registration.domain.CustomerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Class JpaCustomerRepository do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Repository
public class JpaCustomerRepository implements CustomerRepository {
/**
 * Armazena o atributo customerEntityRepository e o estado necessário ao componente.
 */
    private final CustomerEntityRepository customerEntityRepository;
/**
 * Armazena o atributo publisher e o estado necessário ao componente.
 */
    private final ApplicationEventPublisher publisher;

/**
 * Executa a operação JpaCustomerRepository, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customerEntityRepository parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    public JpaCustomerRepository(CustomerEntityRepository customerEntityRepository,
                                 ApplicationEventPublisher publisher) {
        this.customerEntityRepository = customerEntityRepository;
        this.publisher = publisher;
    }

/**
 * Executa a operação save, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customer parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Override
    public Customer save(Customer customer) {
            var entity = mapper(customer);
            customerEntityRepository.save(entity);

            publisher.publishEvent(new CustomerCreated(customer.getId().id().toString(), customer.getName()));

            return customer;
    }

/**
 * Executa a operação findAll, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    @Override
    public List<Customer> findAll(){
        var iterable = customerEntityRepository.findAll();

        return StreamSupport.stream(iterable.spliterator(), false)
                .map(JpaCustomerRepository::mapper)
                .toList();
    }

/**
 * Executa a operação mapper, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customer parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    private static dio.marketplace.registration.infrastructure.persistence.entity.Customer mapper (Customer customer){
        var entity = new dio.marketplace.registration.infrastructure.persistence.entity.Customer();

        entity.setId(customer.getId().id());
        entity.setFirstName(customer.getName());
        entity.setEmail(customer.getEmail());

        return entity;
    }

/**
 * Executa a operação mapper, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param entity parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    private static Customer mapper(dio.marketplace.registration.infrastructure.persistence.entity.Customer entity){
        String fullName = Optional.ofNullable(entity.getLastName())
                .map(lastName -> entity.getFirstName() + " " + lastName)
                .orElseGet(entity::getFirstName);
        return new Customer(new CustomerId(entity.getId()), fullName, entity.getEmail());
    }

}
