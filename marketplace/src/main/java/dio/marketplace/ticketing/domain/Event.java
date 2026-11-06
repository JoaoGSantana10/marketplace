package dio.marketplace.ticketing.domain;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A classe Event representa um evento no sistema de ticketing.
 * Ela contém informações sobre o evento, como seu identificador único (UUID),
 * um identificador de correlação (EventId) e um mapa de setores para listas de
 * assentos disponíveis (Map<Sector, List<Seat>>).
 * A classe possui um construtor que inicializa o identificador único do evento
 * com um UUID aleatório, cria um EventId a partir do identificador de correlação fornecido
 * e armazena o mapa de setores e assentos.
 *
 * A classe é anotada com @Getter do Lombok, o que gera automaticamente os métodos
 * getters para os campos privados da classe, permitindo o acesso a eles de forma segura.
 */

@Getter
public class Event {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private UUID id;
/**
 * Armazena o atributo correlationId e o estado necessário ao componente.
 */
    private EventId correlationId;
/**
 * Armazena o atributo seats e o estado necessário ao componente.
 */
    private Map<Sector, List<Seat>> seats;

/**
 * Executa a operação Event, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param correlationId parâmetro utilizado pela operação.
 * @param Map<Sector parâmetro utilizado pela operação.
 * @param seats parâmetro utilizado pela operação.
 */
    public Event(String correlationId, Map<Sector, List<Seat>> seats) {
        this.id = UUID.randomUUID();
        this.correlationId = new EventId(correlationId);
        this.seats = seats;
    }
}
