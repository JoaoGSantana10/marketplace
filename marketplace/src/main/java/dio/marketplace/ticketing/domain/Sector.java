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
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private UUID id;
/**
 * Armazena o atributo correlationId e o estado necessário ao componente.
 */
    private SectorId correlationId;
/**
 * Armazena o atributo price e o estado necessário ao componente.
 */
    private BigDecimal price;

/**
 * Executa a operação Sector, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param correlationId parâmetro utilizado pela operação.
 * @param price parâmetro utilizado pela operação.
 */
    public Sector (String correlationId, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.correlationId = new SectorId(correlationId);
        this.price = price;
    }
}
