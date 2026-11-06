package dio.marketplace.catalog.infrastructure.persistence.repository;

import dio.marketplace.catalog.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class MongoEventMetadataRepository do módulo persistence.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Repository
public class MongoEventMetadataRepository implements EventMetadataRepository {

/**
 * Armazena o atributo eventMetadataEntityRepository e o estado necessário ao componente.
 */
    private final EventMetadataEntityRepository eventMetadataEntityRepository;

/**
 * Executa a operação MongoEventMetadataRepository, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventMetadataEntityRepository parâmetro utilizado pela operação.
 */
    public MongoEventMetadataRepository(EventMetadataEntityRepository eventMetadataEntityRepository) {
        this.eventMetadataEntityRepository = eventMetadataEntityRepository;
    }

/**
 * Executa a operação findByEventId, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventId parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Override
    public Optional<EventMetadata> findByEventId(EventId eventId) {
        return eventMetadataEntityRepository.findByEventId(eventId.id()).map(MongoEventMetadataRepository::mapper);
    }
    // Monta um mapa de setor com uma lista de assentos e pega o que veio do banco e trasnforma para a nossa classe
/**
 * Executa a operação mapper, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventMetadata parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    private static EventMetadata mapper(dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata eventMetadata) {
        var sectors = eventMetadata.getSectors().stream()
                .map(MongoEventMetadataRepository::mapper)
                .collect(Collectors.toMap(
                        sector -> sector.getId().name(),
                        Function.identity()
                ));
        var seats = eventMetadata.getSeats().stream()
                .map(MongoEventMetadataRepository::mapper)
                .collect(Collectors.groupingBy(
                        seat -> sectors.get(seat.getSectorId().name())
                ));
        return new EventMetadata(
                eventMetadata.getEventDescription(),
                eventMetadata.getTechnicalRequirements(),
                seats);
    }

/**
 * Executa a operação mapper, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param seat parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
        private static Seat mapper (dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata.Seat seat){
            return new Seat(new SeatId(seat.getCode()), new SectorId(seat.getSectorName()));
        }

/**
 * Executa a operação mapper, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata.Sector parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
        private static Sector mapper (dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata.Sector
        sector){
            return new Sector(new SectorId(sector.getName()), sector.getPrice());
        }

}
