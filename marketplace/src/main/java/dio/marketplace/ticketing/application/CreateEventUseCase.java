package dio.marketplace.ticketing.application;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.common.infrastructure.event.dto.EventUpdated;
import dio.marketplace.ticketing.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
//Faça um docstring para essa classe explicando tudo do que ela faz e como ela funciona
/**
 * A classe CreateEventUseCase é responsável por lidar com a criação de eventos no sistema de ticketing.
 * Ela atua como um caso de uso que encapsula a lógica de negócio relacionada à criação de um novo evento.
 *
 * A classe possui uma dependência do CustomerRepository, que é injetada através do construtor. O CustomerRepository
 * é utilizado para persistir os dados do evento no banco de dados.
 *
 * O método execute recebe um evento do tipo CustomerCreated, que contém as informações necessárias para criar um novo evento,
 * como o identificador único (id) e o nome do evento. Dentro do método, um novo objeto Customer é criado utilizando os dados
 * fornecidos pelo evento, e em seguida, o evento é salvo no repositório através do método save do CustomerRepository.
 *
 * Em resumo, a classe CreateEventUseCase encapsula a lógica de criação de eventos, garantindo que os dados sejam
 * persistidos corretamente no sistema.
 */

@Service
public class CreateEventUseCase {
/**
 * Armazena o atributo eventRepository e o estado necessário ao componente.
 */
    private final EventRepository eventRepository;

/**
 * Executa a operação CreateEventUseCase, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventRepository parâmetro utilizado pela operação.
 */
    public CreateEventUseCase(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

/**
 * Executa a operação execute, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    public void execute(EventUpdated event){
        Map<Sector, List<Seat>> seats = event.sectors().stream()
                .collect(Collectors.toMap(
                        sector -> new Sector(sector.id(), sector.price()),
                        sector -> sector.seats().stream()
                                .map(seatDto -> new Seat(seatDto.number()))
                                .collect(Collectors.toList())
                ));
        eventRepository.save(new Event(event.id(), seats));
    }

}
