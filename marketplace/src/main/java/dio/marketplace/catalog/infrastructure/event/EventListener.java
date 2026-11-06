package dio.marketplace.catalog.infrastructure.event;

import dio.marketplace.catalog.infrastructure.persistence.entity.Event;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class EventListener do módulo infrastructure.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
public class EventListener {
    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);

/**
 * Executa a operação onEventCreated, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @PostPersist
    public void onEventCreated(Event event) {
        logger.info("Event created via @PostPersist {}", event);
    }

/**
 * Executa a operação onEventUpdated, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @PostUpdate
    public void onEventUpdated(Event event){
        logger.info("Event updated via @PostUpdate {}", event);
    }

/**
 * Executa a operação onEventRemoved, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @PostRemove
    public void onEventRemoved(Event event){
        logger.info("Event deleted via @PostRemove {}", event);
    }
}
