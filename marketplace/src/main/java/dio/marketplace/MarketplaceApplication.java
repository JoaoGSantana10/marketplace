package dio.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Class MarketplaceApplication do módulo dio.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@SpringBootApplication
@EnableAsync // disponibiliza trabalhar com virtual threads
@EnableCaching // disponibiliza trabalhar com caching
public class MarketplaceApplication {

/**
 * Executa a operação main, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param args parâmetro utilizado pela operação.
 */
    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
    }

}
