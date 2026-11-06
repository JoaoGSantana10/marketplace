package dio.marketplace.registration.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Class Address do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Entity
@Data
@RequiredArgsConstructor
public class Address {

/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

/**
 * Armazena o atributo state e o estado necessário ao componente.
 */
    private String street, postalCode, city, state;

/**
 * Armazena o atributo createdOn e o estado necessário ao componente.
 */
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdOn;

/**
 * Executa a operação toString, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    public String toString() {
        return String.format("%s, %s, %s, %s", street, postalCode, city, state);
    }
}
