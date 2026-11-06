package dio.marketplace.ticketing.domain;

import lombok.Getter;
import java.util.UUID;

/**
 * A classe Seat representa um assento no sistema de ticketing.
 * Ela possui um identificador único (id) do tipo UUID e um identificador de correlação (correlationId) do tipo SeatId.
 * O construtor da classe recebe uma string que é usada para criar o identificador de correlação.
 */
@Getter
public class Seat {
    private UUID id;
    private SeatId correlationId;

    public Seat(String correlationId) {
        this.id = UUID.randomUUID();
        this.correlationId = new SeatId(correlationId);
    }
}
