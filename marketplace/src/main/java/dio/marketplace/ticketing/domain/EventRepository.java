package dio.marketplace.ticketing.domain;

/**
 * A interface EventRepository define o contrato para o repositório de eventos no sistema de ticketing.
 * Ela especifica os métodos que devem ser implementados para salvar eventos no repositório.
 */
public interface EventRepository {
     void save(Event event);
}
