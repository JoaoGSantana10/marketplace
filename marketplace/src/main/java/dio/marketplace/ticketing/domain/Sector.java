package dio.marketplace.ticketing.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A classe Sector representa um setor no sistema de ticketing.
 * Ela possui um identificador único (id) do tipo UUID, um identificador de correlação (correlationId) do tipo SectorId e um preço (price) do tipo BigDecimal.
 * O construtor da classe recebe uma string que é usada para criar o identificador de correlação e um BigDecimal que é usado para definir o preço.
 */
@Getter
public class Sector {
    private UUID id;
    private SectorId correlationId;
    private BigDecimal price;

    public Sector (String correlationId, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.correlationId = new SectorId(correlationId);
        this.price = price;
    }
}
