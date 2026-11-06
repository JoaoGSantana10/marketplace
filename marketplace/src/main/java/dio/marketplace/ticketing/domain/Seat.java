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
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private UUID id;
/**
 * Armazena o atributo correlationId e o estado necessário ao componente.
 */
    private SeatId correlationId;

/**
 * Executa a operação Seat, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param correlationId parâmetro utilizado pela operação.
 */
    public Seat(String correlationId) {
        this.id = UUID.randomUUID();
        this.correlationId = new SeatId(correlationId);
    }
}
