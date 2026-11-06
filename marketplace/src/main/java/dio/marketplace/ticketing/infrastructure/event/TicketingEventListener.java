package dio.marketplace.ticketing.infrastructure.event;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.common.infrastructure.event.dto.EventUpdated;
import dio.marketplace.ticketing.application.CreateCustomerUseCase;
import dio.marketplace.ticketing.application.CreateEventUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * A classe TicketingEventListener é responsável por ouvir eventos relacionados ao sistema de ticketing.
 * Ela utiliza a anotação @Component para ser registrada como um bean no contexto do Spring.
 *
 * A classe possui um logger para registrar informações sobre os eventos recebidos.
 * Ela depende do CreateCustomerUseCase, que é injetado através do construtor, para processar eventos de criação de clientes.
 *
 * A classe possui dois métodos anotados com @EventListener e @Async:
 * 1. handle(CustomerCreated event): Este método é chamado quando um evento CustomerCreated é publicado.
 *    Ele registra a recepção do evento e chama o método execute do CreateCustomerUseCase para processar o evento.
 *
 * 2. handle(EventUpdated event): Este método é chamado quando um evento EventUpdated é publicado.
 *    Ele registra a recepção do evento, mas atualmente não realiza nenhuma ação adicional.
 *
 * A anotação @Async permite que os métodos sejam executados de forma assíncrona, permitindo que o processamento dos eventos não bloqueie o fluxo principal da aplicação.
 */
@Component
public class TicketingEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TicketingEventListener.class);

/**
 * Armazena o atributo createCustomerUseCase e o estado necessário ao componente.
 */
    private final CreateCustomerUseCase createCustomerUseCase;
/**
 * Armazena o atributo createEventUseCase e o estado necessário ao componente.
 */
    private final CreateEventUseCase createEventUseCase;

/**
 * Executa a operação TicketingEventListener, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param createCustomerUseCase parâmetro utilizado pela operação.
 * @param createEventUseCase parâmetro utilizado pela operação.
 */
    public TicketingEventListener(CreateCustomerUseCase createCustomerUseCase, CreateEventUseCase createEventUseCase){
        this.createCustomerUseCase = createCustomerUseCase;
        this.createEventUseCase = createEventUseCase;
    }

/**
 * Executa a operação handle, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @EventListener
    @Async
    public void handle(CustomerCreated event) {
        logger.info("Customer created received {}", event);
        createCustomerUseCase.execute(event);
    }

/**
 * Executa a operação handle, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param event parâmetro utilizado pela operação.
 */
    @EventListener
    @Async
    public void handle(EventUpdated event) {
        logger.info("EventUpdated received {}", event);
        createEventUseCase.execute(event);
    }

}
