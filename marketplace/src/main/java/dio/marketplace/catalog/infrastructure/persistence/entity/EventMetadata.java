package dio.marketplace.catalog.infrastructure.persistence.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class EventMetadata do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Data
@Document
@RequiredArgsConstructor
public class EventMetadata {

/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    private String id;

/**
 * Armazena o atributo eventId e o estado necessário ao componente.
 */
    @NotNull
    private UUID eventId;

/**
 * Armazena o atributo eventDescription e o estado necessário ao componente.
 */
    private String eventDescription;

/**
 * Armazena o atributo technicalRequirements e o estado necessário ao componente.
 */
    private Map<String, Object> technicalRequirements;

/**
 * Armazena o atributo sectors e o estado necessário ao componente.
 */
    private List<Sector> sectors;

/**
 * Armazena o atributo seats e o estado necessário ao componente.
 */
    private List<Seat> seats;

/**
 * Armazena o atributo createdOn e o estado necessário ao componente.
 */
    @CreatedDate
    private Instant createdOn;

/**
 * Armazena o atributo updatedAt e o estado necessário ao componente.
 */
    @LastModifiedDate
    private Instant updatedAt;

/**
 * Class Sector do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sector {
/**
 * Armazena o atributo name e o estado necessário ao componente.
 */
        private String name;
/**
 * Armazena o atributo price e o estado necessário ao componente.
 */
        private BigDecimal price;
    }

/**
 * Class Seat do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Seat {
/**
 * Armazena o atributo code e o estado necessário ao componente.
 */
        private String code;
/**
 * Armazena o atributo sectorName e o estado necessário ao componente.
 */
        private String sectorName;
    }
}
