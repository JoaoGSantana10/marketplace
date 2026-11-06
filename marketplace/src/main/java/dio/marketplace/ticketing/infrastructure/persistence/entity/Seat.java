package dio.marketplace.ticketing.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * A classe Seat representa um assento no sistema de ticketing.
 * Ela é anotada com @Entity, indicando que é uma entidade JPA que será mapeada para uma tabela no banco de dados.
 *
 * A anotação @Data do Lombok gera automaticamente os métodos getters, setters, equals, hashCode e toString para os campos da classe.
 *
 * A anotação @NoArgsConstructor gera um construtor sem argumentos, necessário para a criação de instâncias da entidade pelo JPA.
 *
 * A anotação @AllArgsConstructor gera um construtor com argumentos para todos os campos da classe, permitindo a criação de instâncias com valores específicos.
 *
 * A classe possui dois campos:
 * 1. id: Um identificador único do tipo UUID, anotado com @Id, indicando que é a chave primária da entidade.
 * 2. correlationId: Uma string que representa o identificador de correlação do assento, utilizada para associar o assento a um evento específico.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    private UUID id;

    private String correlationId;
}
