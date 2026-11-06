package dio.marketplace.catalog.domain;

import java.util.List;

/**
 * Interface EventRepository do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public interface EventRepository {
    List<Event> findAll();
}
