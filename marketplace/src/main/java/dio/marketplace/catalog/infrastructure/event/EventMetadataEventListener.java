package dio.marketplace.catalog.infrastructure.event;

import dio.marketplace.catalog.infrastructure.persistence.entity.EventMetadata;
import dio.marketplace.common.infrastructure.event.dto.EventUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

/**
 * Class EventMetadataEventListener do módulo infrastructure.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Component
public class EventMetadataEventListener extends AbstractMongoEventListener<EventMetadata> {
    private final Logger logger = LoggerFactory.getLogger(EventMetadataEventListener.class);
/**
 * Armazena o atributo applicationEventPublisher e o estado necessário ao componente.
 */
    private final ApplicationEventPublisher applicationEventPublisher;

/**
 * Executa a operação EventMetadataEventListener, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param applicationEventPublisher parâmetro utilizado pela operação.
 */
    public EventMetadataEventListener(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

/**
 * Executa a operação onAfterSave, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @Override
    public void onAfterSave(AfterSaveEvent<EventMetadata> event){
        logger.info("Event metadata save via onAfterSave {}", event.getDocument());
        this.applicationEventPublisher.publishEvent(EventUpdated.from(event.getSource()));
    }

/**
 * Executa a operação onAfterDelete, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @Override
    public void onAfterDelete(AfterDeleteEvent<EventMetadata> event){
        logger.info("Event metadata delete via onAfterDelete {}", event.getDocument());
    }
}
