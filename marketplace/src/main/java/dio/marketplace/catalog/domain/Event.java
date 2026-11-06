package dio.marketplace.catalog.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Optional;

/**
 * Class Event do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Getter
@Setter
public class Event {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private EventId id;
/**
 * Armazena o atributo title e o estado necessário ao componente.
 */
    private String title;
/**
 * Armazena o atributo date e o estado necessário ao componente.
 */
    private Instant date;
/**
 * Armazena o atributo metadata e o estado necessário ao componente.
 */
    private Optional<EventMetadata> metadata;

/**
 * Executa a operação Event, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param id parâmetro utilizado pela operação.
 * @param title parâmetro utilizado pela operação.
 * @param date parâmetro utilizado pela operação.
 * @param metadata parâmetro utilizado pela operação.
 */
    public Event(EventId id, String title, Instant date, Optional<EventMetadata> metadata) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.metadata = metadata;
    }
}
