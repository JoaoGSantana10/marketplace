package dio.marketplace.ticketing.domain;

import lombok.Getter;
import java.util.UUID;
/**
 * A classe Customer representa um cliente no sistema de ticketing.
 * Ela contém informações sobre o cliente, como seu identificador único (UUID),
 * um identificador de correlação (CustomerId) e o nome do cliente.
 * A classe possui um construtor que inicializa o identificador único do cliente
 * com um UUID aleatório, cria um CustomerId a partir do identificador de correlação fornecido
 * e armazena o nome do cliente.
 *
 * A classe é anotada com @Getter do Lombok, o que gera automaticamente os métodos
 * getters para os campos privados da classe, permitindo o acesso a eles de forma segura.
 */

@Getter
public class Customer {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private UUID id;
/**
 * Armazena o atributo correlationId e o estado necessário ao componente.
 */
    private CustomerId correlationId;
/**
 * Armazena o atributo name e o estado necessário ao componente.
 */
    private String name;

/**
 * Executa a operação Customer, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param correlationId parâmetro utilizado pela operação.
 * @param name parâmetro utilizado pela operação.
 */
    public Customer(String correlationId, String name){
        this.id = UUID.randomUUID();
        this.correlationId = new CustomerId(correlationId);
        this.name = name;
    }
}
