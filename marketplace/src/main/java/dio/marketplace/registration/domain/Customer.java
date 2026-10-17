package dio.marketplace.registration.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Customer {
    private CustomerId id;
    private String name;
    private String email;


    public Customer(CustomerId id, String name, String email) {
        // validando se não é nulo
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(email, "Email must not be null");

        this.id = id;
        this.name = name;
        this.email = email;
    }

    // A ideia é que o ID seja criado
    public Customer(String name, String email) {
        // Quando a gente criar um customer ele vai utilizar esse construtor e ele vai gerar um random UUID
        this(new CustomerId(), name, email);
    }
}
