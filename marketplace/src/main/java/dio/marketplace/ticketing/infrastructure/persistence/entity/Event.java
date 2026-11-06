package dio.marketplace.ticketing.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * A classe Event representa um evento no sistema de ticketing e é mapeada para uma tabela no banco de dados.
 *
 * Anotações:
 * - @Entity: Indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados.
 * - @Data: Gera automaticamente os métodos getters, setters, equals, hashCode e toString para os campos da classe.
 * - @NoArgsConstructor: Gera um construtor sem argumentos, necessário para a criação de instâncias da entidade pelo JPA.
 * - @AllArgsConstructor: Gera um construtor com argumentos para todos os campos da classe.
 *
 * A classe Event é usada para representar eventos no sistema de ticketing,
 * permitindo a gestão de setores e cadeiras associadas a cada evento.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    private UUID id;

    private UUID correlationId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<Sector> sectors = new ArrayList<>();
}
