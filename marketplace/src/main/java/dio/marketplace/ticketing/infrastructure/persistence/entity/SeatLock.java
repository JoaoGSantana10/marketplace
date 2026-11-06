package dio.marketplace.ticketing.infrastructure.persistence.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

/**
 * A classe SeatLock representa uma entidade de bloqueio de assento no contexto de um sistema de ticketing.
 * Ela é utilizada para armazenar informações sobre o bloqueio de assentos por clientes em um banco de dados Redis.
 * As anotações utilizadas nesta classe são:
 * - @RedisHash: Indica que esta classe será armazenada em um hash do Redis. O valor "seat_locks" especifica o
 * nome do hash, e o parâmetro timeToLive define o tempo de vida do registro em segundos (neste caso, 30 segundos).
 * - @Id: Indica que o campo 'id' é a chave primária da entidade, que será usada para identificar unicamente cada bloqueio de assento.
 * - @Indexed: Indica que o campo 'customerId' será indexado no Redis, permitindo consultas mais rápidas baseadas nesse campo.
 * A classe possui os seguintes atributos:
 * - id: Um identificador único para o bloqueio de assento.
 * - customerId: O identificador do cliente que realizou o bloqueio do assento.
 * - createdAt: A data e hora em que o bloqueio de assento foi criado, representada como um objeto Instant.
 */

@RedisHash(value = "seat_locks", timeToLive = 30)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatLock {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    private String id;

/**
 * Armazena o atributo customerId e o estado necessário ao componente.
 */
    @Indexed
    private String customerId;

/**
 * Armazena o atributo createdAt e o estado necessário ao componente.
 */
    private Instant createdAt;
}
