package dio.marketplace.ticketing.domain;

import org.springframework.util.Assert;
/**
 * A classe SeatId representa o identificador de um assento no sistema de ticketing.
 * Ela é usada para criar um identificador único para cada assento.
 */
public record SeatId(String id) {
    public SeatId{
        Assert.notNull(id, "id must not be null");
    }
}
