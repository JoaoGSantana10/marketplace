package dio.marketplace.catalog.application.dto;


import dio.marketplace.catalog.domain.Event;
import dio.marketplace.catalog.domain.EventMetadata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* Um data transfer object (DTO) vai ser uma camada de transformação de dados, vamos receber o evento vamos transformar
 ele em um evento output. E aí meu controller vai ter acesso a isso. */
public record EventOutput(
        String id,
        String title,
        Instant date,
        EventMetadataOutput metadata
) implements Serializable {

/**
 * Record EventMetadataOutput do módulo application.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
    public record EventMetadataOutput(
            String eventDescription,
            Map<String, Object> technicalRequirements,
            Map<String, List<SeatOutput>> seatsBySector
    ) implements Serializable {
/**
 * Record SeatOutput do módulo application.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
        public record SeatOutput(
                String id,
                String sectorId,
                BigDecimal price

        ) implements Serializable {
        }

/**
 * Executa a operação from, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param metadata parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
        public static EventMetadataOutput from(EventMetadata metadata) {
            Map<String, List<SeatOutput>> seats =
                    metadata.seats().entrySet().stream()
                            .collect(Collectors.toMap(
                                    entry -> entry.getKey().getId().name(),
                                    entry -> entry.getValue().stream()
                                            .map(seat -> new EventOutput.EventMetadataOutput.SeatOutput(
                                                    seat.getId().seatNumber(),
                                                    seat.getSectorId().name(),
                                                    entry.getKey().getPrice()
                                            ))
                                            .toList()
                            ));
            return new EventOutput.EventMetadataOutput(
                    metadata.eventDescription(),
                    metadata.technicalRequirements(),
                    seats
            );
        }
    }

/**
 * Executa a operação from, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    public static EventOutput from(Event event){
        return new EventOutput(
                event.getId().id().toString(),
                event.getTitle(),
                event.getDate(),
                event.getMetadata()
                        .map(EventMetadataOutput::from)
                        .orElse(null)
        );
    }
}
