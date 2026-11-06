package dio.marketplace.registration.infrastructure.event;

import dio.marketplace.common.infrastructure.event.dto.CustomerCreated;
import dio.marketplace.registration.infrastructure.persistence.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;


/**
 * Class CustomerEventHandler do módulo infrastructure.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Component
@RepositoryEventHandler // dispara os eventos de acordo com a ação que eu fiz
public class CustomerEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomerEventHandler.class);

/**
 * Armazena o atributo publisher e o estado necessário ao componente.
 */
    private final ApplicationEventPublisher publisher;

/**
 * Executa a operação CustomerEventHandler, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param publisher parâmetro utilizado pela operação.
 */
    public CustomerEventHandler(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    // Assim que eu criar um customer pela API vai ser publicado um customer created event
/**
 * Executa a operação handleAfterCreate, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customer parâmetro utilizado pela operação.
 */
    @HandleAfterCreate // acesso a todos os evento que o Hibernate vai mandar para mim
    public void handleAfterCreate(Customer customer){
        logger.warn("CustomerEventHandler#handleAfterCreate");
        publisher.publishEvent(new CustomerCreated(customer.getId().toString(), customer.getFirstName()));
    }

/**
 * Executa a operação handleAfterSave, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customer parâmetro utilizado pela operação.
 */
    @HandleAfterSave
    public void handleAfterSave(Customer customer){
        logger.warn("CustomerEventHandler#handleAfterSave");
    }

/**
 * Executa a operação handleAfterDelete, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param customer parâmetro utilizado pela operação.
 */
    @HandleAfterDelete
    public void handleAfterDelete(Customer customer){
        logger.warn("CustomerEventHandler#handleAfterDelete");
    }
}
