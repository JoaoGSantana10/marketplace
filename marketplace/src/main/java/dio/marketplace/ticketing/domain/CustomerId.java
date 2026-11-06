package dio.marketplace.ticketing.domain;

import org.springframework.util.Assert;

import java.util.UUID;
/**
 * A classe CustomerId representa o identificador de um cliente no sistema de ticketing.
 * Ela é uma record do Java que contém um campo id do tipo UUID.
 * A classe possui um construtor que recebe um UUID e o valida para garantir que não seja nulo.
 * Também possui um construtor alternativo que recebe uma string e a converte para um UUID.
 */
public record CustomerId(UUID id) {
    public CustomerId {
        Assert.notNull(id, "id must not be null");
    }

    public CustomerId(String id){
        this(UUID.fromString(id));
    }
}
