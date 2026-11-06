package dio.marketplace.ticketing.application;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.ticketing.domain.Customer;
import dio.marketplace.ticketing.domain.CustomerRepository;
import org.springframework.stereotype.Service;

/**
 * A classe CreateCustomerUseCase é responsável por lidar com a criação de clientes no sistema de ticketing.
 * Ela atua como um caso de uso que encapsula a lógica de negócio relacionada à criação de um novo cliente.
 *
 * A classe possui uma dependência do CustomerRepository, que é injetada através do construtor. O CustomerRepository
 * é utilizado para persistir os dados do cliente no banco de dados.
 *
 * O método execute recebe um evento do tipo CustomerCreated, que contém as informações necessárias para criar um novo cliente,
 * como o identificador único (id) e o nome do cliente. Dentro do método, um novo objeto Customer é criado utilizando os dados
 * fornecidos pelo evento, e em seguida, o cliente é salvo no repositório através do método save do CustomerRepository.
 *
 * Em resumo, a classe CreateCustomerUseCase encapsula a lógica de criação de clientes, garantindo que os dados sejam
 * persistidos corretamente no sistema.
 */

@Service
public class CreateCustomerUseCase {
    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public void execute(CustomerCreated event){
        var customer = new Customer(event.id(), event.name());
        customerRepository.save(customer);
    }

}
