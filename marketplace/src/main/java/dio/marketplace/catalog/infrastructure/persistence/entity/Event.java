package dio.marketplace.catalog.infrastructure.persistence.entity;

import dio.marketplace.catalog.infrastructure.event.EventListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.Instant;
import java.util.UUID;

@Entity
@EntityListeners(EventListener.class) // Injetamos um EventListener
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Instant date;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdOn;
}
