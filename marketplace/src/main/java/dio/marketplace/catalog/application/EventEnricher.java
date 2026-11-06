package dio.marketplace.catalog.application;

import dio.marketplace.catalog.domain.Event;
import dio.marketplace.catalog.domain.EventMetadataRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Class EventEnricher do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Service
public class EventEnricher {
    private static final Logger logger = LoggerFactory.getLogger(EventEnricher.class.getName());

/**
 * Armazena o atributo eventMetadataRepository e o estado necessário ao componente.
 */
    private final EventMetadataRepository eventMetadataRepository;

/**
 * Executa a operação EventEnricher, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventMetadataRepository parâmetro utilizado pela operação.
 */
    public EventEnricher(EventMetadataRepository eventMetadataRepository) {
        this.eventMetadataRepository = eventMetadataRepository;
    }

/**
 * Executa a operação enrich, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Async
    public CompletableFuture<Event> enrich(Event event) {
        logger.info("Enriching event: {}", event);

        // para cada item que veio na minha lista de eventos eu vou fazer um matadata
        var metadata = eventMetadataRepository.findByEventId(event.getId());
        event.setMetadata(metadata);

        return CompletableFuture.completedFuture(event);
    }
}
