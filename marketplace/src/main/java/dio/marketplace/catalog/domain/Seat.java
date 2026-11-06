package dio.marketplace.catalog.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Class Seat do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Getter
@Setter
public class Seat {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private SeatId id;
/**
 * Armazena o atributo sectorId e o estado necessário ao componente.
 */
    private SectorId sectorId;

/**
 * Executa a operação Seat, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param id parâmetro utilizado pela operação.
 * @param sectorId parâmetro utilizado pela operação.
 */
    public Seat(SeatId id, SectorId sectorId) {
        this.id = id;
        this.sectorId = sectorId;
    }
}
