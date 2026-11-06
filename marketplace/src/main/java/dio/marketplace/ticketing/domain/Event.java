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
    private UUID id;
    private EventId correlationId;
    private Map<Sector, List<Seat>> seats;

    public Event(String correlationId, Map<Sector, List<Seat>> seats) {
        this.id = UUID.randomUUID();
        this.correlationId = new EventId(correlationId);
        this.seats = seats;
    }
}
