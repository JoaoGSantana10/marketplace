package dio.marketplace.ticketing.domain;

/**
 * A interface CustomerRepository define o contrato para o repositório de clientes no sistema de ticketing.
 * Ela define métodos para salvar clientes no repositório.
 */
public interface CustomerRepository {
    void save(Customer customer);
}
