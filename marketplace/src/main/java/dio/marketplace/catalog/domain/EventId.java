package dio.marketplace.catalog.domain;

import java.util.UUID;

/**
 * Record EventId do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public record EventId(UUID id) {
/**
 * Executa a operação EventId, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    public EventId() {
        this(UUID.randomUUID());
    }
}
