package dio.marketplace.catalog.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Class Sector do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Getter
@Setter
public class Sector {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private SectorId id;
/**
 * Armazena o atributo price e o estado necessário ao componente.
 */
    private BigDecimal price;

/**
 * Executa a operação Sector, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param id parâmetro utilizado pela operação.
 * @param price parâmetro utilizado pela operação.
 */
    public Sector(SectorId id, BigDecimal price){
        this.id = id;
        this.price = price;

    }

}
