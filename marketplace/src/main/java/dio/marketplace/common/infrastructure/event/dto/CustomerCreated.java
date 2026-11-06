package dio.marketplace.common.infrastructure.event.dto;

/**
 * Record CustomerCreated do módulo event.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public record CustomerCreated(String id, String name) {
}
