package dio.marketplace.catalog.domain;

import java.util.List;
import java.util.Map;

/**
 * Record EventMetadata do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public record EventMetadata(String eventDescription,
                            Map<String, Object> technicalRequirements,
                            Map<Sector, List<Seat>> seats) {
}
