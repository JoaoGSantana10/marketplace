package dio.marketplace.catalog.application;

import dio.marketplace.catalog.domain.Event;
import dio.marketplace.catalog.domain.EventMetadataRepository;
import dio.marketplace.catalog.domain.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // O Spring Boot vai injetar ele para mim no controller
public class BrowseShowcaseUseCase {

    private static final Logger logger = LoggerFactory.getLogger(BrowseShowcaseUseCase.class);

    private final EventRepository eventRepository;
    private final EventMetadataRepository eventMetadataRepository;

    public BrowseShowcaseUseCase(EventRepository eventRepository,  EventMetadataRepository eventMetadataRepository) {
        this.eventRepository = eventRepository;
        this.eventMetadataRepository = eventMetadataRepository;
    }

    public List<Event> execute() {
        var events = eventRepository.findAll().stream().map(event -> {
            logger.info("Enriching event: {}", event);

            // para cada item que veio na minha lista de eventos eu vou fazer um matadata
            var metadata = eventMetadataRepository.findByEventId(event.getId());
            event.setMetadata(metadata);

            return event;
        }).toList();

        return events;
    }
}
