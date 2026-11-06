package dio.marketplace.common.infrastructure.event.dto;

import dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata;

import java.math.BigDecimal;
import java.util.List;

// Relação aos eventos e metadados
/**
 * Record EventUpdated do módulo event.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public record EventUpdated(String id, List<Sector> sectors) {

/**
 * Executa a operação from, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    public static EventUpdated from(EventMetadata event){
        List<Sector> sectors = event.getSectors().stream()
                .map(s -> Sector.from(s, event.getSeats()))
                .toList();

        return new EventUpdated(event.getEventId().toString(), sectors);
    }

/**
 * Record Sector do módulo event.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
    public record Sector(String id, BigDecimal price, List<Seat> seats) {

/**
 * Executa a operação from, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param sector parâmetro utilizado pela operação.
 * @param allSeats parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
        public static Sector from(EventMetadata.Sector sector, List<EventMetadata.Seat> allSeats) {

            List<Seat> sectorSeats = allSeats.stream()
                    .filter(seat -> seat.getSectorName().equals(sector.getName()))
                    .map(Seat::from)
                    .toList();

            return new Sector(sector.getName(), sector.getPrice(), sectorSeats);
        }

/**
 * Record Seat do módulo event.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
        public record Seat(String number){
/**
 * Executa a operação from, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param seat parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
            public static Seat from(EventMetadata.Seat seat){
                return new Seat(seat.getCode());
            }
        }
    }
}
