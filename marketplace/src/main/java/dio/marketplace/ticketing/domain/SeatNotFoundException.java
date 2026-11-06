package dio.marketplace.ticketing.domain;

/**
 * Class SeatNotFoundException do módulo ticketing.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public class SeatNotFoundException extends RuntimeException {
/**
 * Executa a operação SeatNotFoundException, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventId parâmetro utilizado pela operação.
 * @param seatId parâmetro utilizado pela operação.
 */
    public SeatNotFoundException(EventId eventId, SeatId seatId) {
        super("Seat with id " + seatId + "not found");
    }
}
