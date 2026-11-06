package dio.marketplace.registration.domain;

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * Class Customer do módulo registration.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@Getter
public class Customer {
/**
 * Armazena o atributo id e o estado necessário ao componente.
 */
    private CustomerId id;
/**
 * Armazena o atributo name e o estado necessário ao componente.
 */
    private String name;
/**
 * Armazena o atributo email e o estado necessário ao componente.
 */
    private String email;


/**
 * Executa a operação Customer, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param id parâmetro utilizado pela operação.
 * @param name parâmetro utilizado pela operação.
 * @param email parâmetro utilizado pela operação.
 */
    public Customer(CustomerId id, String name, String email) {
        // validando se não é nulo
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(email, "Email must not be null");

        this.id = id;
        this.name = name;
        this.email = email;
    }

    // A ideia é que o ID seja criado
/**
 * Executa a operação Customer, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param name parâmetro utilizado pela operação.
 * @param email parâmetro utilizado pela operação.
 */
    public Customer(String name, String email) {
        // Quando a gente criar um customer ele vai utilizar esse construtor e ele vai gerar um random UUID
        this(new CustomerId(), name, email);
    }
}
