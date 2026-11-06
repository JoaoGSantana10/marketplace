package dio.marketplace.catalog.application;

import dio.marketplace.catalog.application.dto.EventOutput;
import dio.marketplace.catalog.domain.Event;
import dio.marketplace.catalog.domain.EventMetadataRepository;
import dio.marketplace.catalog.domain.EventRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Class BrowseShowcaseUseCase do módulo catalog.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Service // O Spring Boot vai injetar ele para mim no controller
public class BrowseShowcaseUseCase {

    private static final Logger logger = LoggerFactory.getLogger(BrowseShowcaseUseCase.class);

/**
 * Armazena o atributo eventRepository e o estado necessário ao componente.
 */
    private final EventRepository eventRepository;
/**
 * Armazena o atributo eventEnricher e o estado necessário ao componente.
 */
    private final EventEnricher eventEnricher;

/**
 * Executa a operação BrowseShowcaseUseCase, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param eventRepository parâmetro utilizado pela operação.
 * @param eventEnricher parâmetro utilizado pela operação.
 */
    public BrowseShowcaseUseCase(EventRepository eventRepository,  EventEnricher eventEnricher) {
        this.eventRepository = eventRepository;
        this.eventEnricher = eventEnricher;
    }

    // se o resultado não for vazio vai persistir isso no redis
/**
 * Executa a operação execute, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    @Cacheable(value = "showcase", unless = "#result.isEmpty()")
    public List<EventOutput> execute() {
        var futures = eventRepository.findAll().stream().map(eventEnricher::enrich).toList();

        var events = futures.stream()
                .map(CompletableFuture::join) // isso significa que eu vou pegar e executar todos em paralelo
                .map(EventOutput::from)
                .toList();

        logger.info("{} events enriched", events.size());
        return events;
    }
}
