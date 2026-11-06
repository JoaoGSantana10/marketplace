package dio.marketplace.registration.domain;

import java.util.List;

/**
 * Interface CustomerRepository do módulo registration.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public interface CustomerRepository {
    Customer save(Customer customer);
    List<Customer> findAll();
}
