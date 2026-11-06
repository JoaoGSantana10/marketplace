package dio.marketplace.ticketing.infrastructure.http.request;

import dio.marketplace.ticketing.domain.SeatId;

/**
 * Record SeatSelectionRequest do módulo http.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public record SeatSelectionRequest(String id) {
/**
 * Executa a operação toInput, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    public SeatId toInput(){
        return new SeatId(id);
    }
}
