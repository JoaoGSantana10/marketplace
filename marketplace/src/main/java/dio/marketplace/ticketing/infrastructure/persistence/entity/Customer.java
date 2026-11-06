package dio.marketplace.ticketing.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * A classe Customer representa um cliente no sistema de ticketing.
 * Ela é uma entidade persistente mapeada para uma tabela no banco de dados.
 *
 * A classe possui os seguintes campos:
 * - id: Um identificador único do cliente, do tipo UUID, que serve como chave primária da entidade.
 * - correlationId: Um identificador de correlação do cliente, também do tipo UUID, que pode ser usado para rastrear ou correlacionar informações relacionadas ao cliente.
 * - name: O nome do cliente, que é uma string obrigatória (não pode ser nula ou em branco) e é mapeada para uma coluna no banco de dados com a anotação @Column(nullable = false).
 *
 * A classe é anotada com @Entity, indicando que é uma entidade JPA e será mapeada para uma tabela no banco de dados.
 * As anotações @Data, @NoArgsConstructor e @AllArgsConstructor do Lombok são usadas para gerar automaticamente os métodos getters, setters, construtores sem argumentos e construtores com todos os argumentos, respectivamente.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    private UUID id;

/**
 * Armazena o atributo correlationId e o estado necessário ao componente.
 */
    private UUID correlationId;

/**
 * Armazena o atributo name e o estado necessário ao componente.
 */
    @NotBlank
    @Column(nullable = false)
    private String name;
}
