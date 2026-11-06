package dio.marketplace.registration.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Class Customer do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Entity
@Data
@RequiredArgsConstructor
public class Customer {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    private UUID id;

/**
 * Armazena o atributo firstName e o estado necessário ao componente.
 */
    @NotBlank
    @Column(nullable = false)
    private String firstName;

/**
 * Armazena o atributo lastName e o estado necessário ao componente.
 */
    private String lastName;

/**
 * Armazena o atributo email e o estado necessário ao componente.
 */
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

/**
 * Armazena o atributo createOn e o estado necessário ao componente.
 */
    @CreationTimestamp
    @Column(nullable = false,  updatable = false)
    private Instant createOn;

/**
 * Armazena o atributo address e o estado necessário ao componente.
 */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

/**
 * Executa a operação prePersist, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 */
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
