package dio.marketplace.ticketing.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe Sector representa um setor em um evento no sistema de ticketing.
 * Ela é anotada com @Entity, indicando que é uma entidade JPA que será mapeada para uma tabela no banco de dados.
 *
 * A anotação @Data do Lombok gera automaticamente os métodos getters, setters, equals, hashCode e toString para os campos da classe.
 *
 * A anotação @NoArgsConstructor gera um construtor sem argumentos, necessário para a criação de instâncias da entidade pelo JPA.
 *
 * A anotação @AllArgsConstructor gera um construtor com argumentos para todos os campos da classe, permitindo a criação de instâncias com valores específicos.
 *
 * A classe possui quatro campos:
 * 1. id: Um identificador único do tipo UUID, anotado com @Id, indicando que é a chave primária da entidade.
 * 2. correlationId: Uma string que representa o identificador de correlação do setor, utilizada para associar o setor a um evento específico.
 * 3. price: Um BigDecimal que representa o preço dos ingressos para o setor.
 * 4. seats: Uma lista de objetos Seat, representando os assentos disponíveis no setor. É anotada com @OneToMany, indicando um relacionamento de um para muitos com a entidade Seat.
 *    - cascade = CascadeType.ALL: Indica que todas as operações (persistir, mesclar, remover) realizadas no setor serão propagadas para os assentos associados.
 *    - orphanRemoval = true: Indica que se um assento for removido da lista de assentos do setor, ele também será removido do banco de dados.
 *    - @JoinColumn(name = "sector_id"): Especifica a coluna na tabela Seat que será usada como chave estrangeira para referenciar o setor.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    private UUID id;

/**
 * Armazena o atributo correlationId e o estado necessário ao componente.
 */
    private String correlationId;

/**
 * Armazena o atributo price e o estado necessário ao componente.
 */
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sector_id")
    private List<Seat> seats = new ArrayList<>();
}
