package dio.marketplace.registration.domain;

import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Record CustomerId do módulo registration.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public record CustomerId(UUID id) {
    public CustomerId {
        Assert.notNull(id, "Customer id must not be null");
    }

/**
 * Executa a operação CustomerId, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    public CustomerId(){
        this(UUID.randomUUID());
    }
}
