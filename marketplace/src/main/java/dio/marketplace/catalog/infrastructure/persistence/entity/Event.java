package dio.marketplace.catalog.infrastructure.persistence.entity;

import dio.marketplace.catalog.infrastructure.event.EventListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.Instant;
import java.util.UUID;

/**
 * Class Event do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Entity
@EntityListeners(EventListener.class) // Injetamos um EventListener
@Data
public class Event {

/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

/**
 * Armazena o atributo title e o estado necessário ao componente.
 */
    @NotBlank
    @Column(nullable = false)
    private String title;

/**
 * Armazena o atributo date e o estado necessário ao componente.
 */
    @Column(nullable = false)
    private Instant date;

/**
 * Armazena o atributo createdOn e o estado necessário ao componente.
 */
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdOn;
}
