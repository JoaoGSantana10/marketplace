package dio.marketplace.ticketing.infrastructure.event;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.common.infrastructure.event.dto.EventUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TicketingEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TicketingEventListener.class);

    @EventListener
    @Async
    public void handle(CustomerCreated event) {
        logger.info("Customer created received {}", event);
    }

    @EventListener
    @Async
    public void handle(EventUpdated event) {
        logger.info("EventUpdated received {}", event);
    }

}
