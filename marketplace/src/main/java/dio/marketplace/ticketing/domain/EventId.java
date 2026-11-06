package dio.marketplace.ticketing.domain;

import org.springframework.util.Assert;
import java.util.UUID;

/**
 * A classe EventId representa o identificador de um evento no sistema de ticketing.
 * Ela é uma record do Java que contém um campo id do tipo UUID.
 * A classe possui um construtor que recebe um UUID e o valida para garantir que não seja nulo.
 * Também possui um construtor alternativo que recebe uma string e a converte para um UUID.
 */

public record EventId(UUID id) {
    public EventId{
        Assert.notNull(id, "id must not be null");
    }

    public EventId(String id){
        this(UUID.fromString(id));
    }
}
