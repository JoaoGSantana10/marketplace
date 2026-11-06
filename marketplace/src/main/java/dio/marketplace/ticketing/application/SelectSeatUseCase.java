package dio.marketplace.ticketing.application;

import dio.marketplace.ticketing.domain.*;
import org.springframework.stereotype.Service;

/**
 * Class SelectSeatUseCase do módulo ticketing.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Service
public class SelectSeatUseCase {
/**
 * Armazena o atributo eventRepository e o estado necessário ao componente.
 */
    private final EventRepository eventRepository;

/**
 * Executa a operação SelectSeatUseCase, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventRepository parâmetro utilizado pela operação.
 */
    public SelectSeatUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

/**
 * Executa a operação execute, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventId parâmetro utilizado pela operação.
 * @param seatId parâmetro utilizado pela operação.
 * @param customerId parâmetro utilizado pela operação.
 */
    public void execute(EventId eventId, SeatId seatId, CustomerId customerId) {
        if (!eventRepository.existsSeat(eventId, seatId)) {
            throw new SeatNotFoundException(eventId, seatId);
        }

        boolean lock = eventRepository.tryLockSeat(eventId, seatId, customerId);
        if (!lock) {
            throw new SeatAlreadyReservedException();
        }

        // aqui a gente poderia seguir com um: Order, Payment, ...
    }
}
