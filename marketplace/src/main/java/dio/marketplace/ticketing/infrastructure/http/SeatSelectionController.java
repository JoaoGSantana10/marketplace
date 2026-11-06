package dio.marketplace.ticketing.infrastructure.http;

import dio.marketplace.ticketing.application.SelectSeatUseCase;
import dio.marketplace.ticketing.domain.CustomerId;
import dio.marketplace.ticketing.domain.EventId;
import dio.marketplace.ticketing.infrastructure.http.request.SeatSelectionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Class SeatSelectionController do módulo infrastructure.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@RestController
@RequestMapping("/ticketing/events/{eventId}/seats")
public class SeatSelectionController {
/**
 * Armazena o atributo selectSeatUseCase e o estado necessário ao componente.
 */
    private final SelectSeatUseCase selectSeatUseCase;

/**
 * Executa a operação SeatSelectionController, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param selectSeatUseCase parâmetro utilizado pela operação.
 */
    public SeatSelectionController(SelectSeatUseCase selectSeatUseCase) {
        this.selectSeatUseCase = selectSeatUseCase;
    }

/**
 * Executa a operação selectSeat, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventId parâmetro utilizado pela operação.
 */
    @PostMapping("/select")
    @ResponseStatus(HttpStatus.CREATED)
    public void selectSeat(@PathVariable String eventId,
                           @RequestBody SeatSelectionRequest request,
                           @RequestHeader("X-CUSTOMER-ID") String customerId) {
        selectSeatUseCase.execute(new EventId(eventId), request.toInput(), new CustomerId(customerId));
    }

}
