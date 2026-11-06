package dio.marketplace.ticketing.infrastructure;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

/**
 * A classe TicketingConfiguration é responsável por configurar a camada de persistência do módulo de ticketing
 * da aplicação. Ela define os beans necessários para a integração com o banco de dados, utilizando JPA e Spring Data.
 *
 * A classe é anotada com @Configuration, indicando que ela contém definições de beans para o contexto do Spring.
 * Além disso, ela utiliza @EnableJpaRepositories para habilitar a criação automática de repositórios JPA
 * na base de pacotes especificada ("dio.marketplace.ticketing"), configurando o EntityManagerFactory e o TransactionManager
 * para o módulo de ticketing.
 *
 * Os principais componentes configurados nesta classe incluem:
 * 1. DataSourceProperties: Define as propriedades do DataSource específico para o módulo de ticketing,
 *    permitindo a configuração de URL, usuário, senha e outras propriedades do banco de dados.
 *
 * 2. HikariDataSource: Cria uma instância do HikariDataSource, que é um pool de conexões eficiente,
 *    utilizando as propriedades definidas no DataSourceProperties.
 *
 * 3. JpaProperties: Define as propriedades específicas do JPA para o módulo de ticketing, como dialeto,
 *    estratégias de geração de schema e outras configurações relacionadas ao JPA.
 *
 * 4. LocalContainerEntityManagerFactoryBean: Configura o EntityManagerFactory para o módulo de ticketing,
 *    especificando o DataSource, as propriedades do JPA e os pacotes onde estão localizadas as entidades JPA.
 *
 * 5. PlatformTransactionManager: Configura o TransactionManager para gerenciar transações no módulo de ticketing,
 *    garantindo que as operações de persistência sejam realizadas dentro de transações apropriadas.
 *
 * Em resumo, esta classe atua como uma ponte entre a aplicação e o banco de dados, garantindo que todas as operações
 * relacionadas à persistência no módulo de ticketing sejam configuradas corretamente e funcionem conforme esperado.
 */
@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "dio.marketplace.ticketing",
        entityManagerFactoryRef = "ticketingEntityManagerFactory",
        transactionManagerRef = "ticketingTransactionManager")
/**
 * Class TicketingConfiguration do módulo ticketing.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@EnableRedisRepositories(basePackages = "dio.marketplace.ticketing", redisTemplateRef = "ticketingRedisTemplate")
public class TicketingConfiguration {
    //Configuração do DataSource o Spring vai injetar quando tiver a utilização do qualifier ticketing
/**
 * Executa a operação ticketingDataSourceProperties, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("ticketing.datasource")
    public DataSourceProperties ticketingDataSourceProperties(){
        return new DataSourceProperties();
    }

/**
 * Executa a operação ticketingDataSource, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param properties parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("ticketing.datasource.configuration")
    public HikariDataSource ticketingDataSource(@Qualifier("ticketing") DataSourceProperties properties){
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

/**
 * Executa a operação ticketingJpaProperties, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("ticketing.jpa")
    public JpaProperties ticketingJpaProperties(){
        return new JpaProperties();
    }

/**
 * Executa a operação ticketingEntityManagerFactory, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param ("ticketing" parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    public LocalContainerEntityManagerFactoryBean ticketingEntityManagerFactory(@Qualifier("ticketing") DataSource dataSource,
                                                                                @Qualifier("ticketing") JpaProperties jpaProperties) {
        var builder = new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                x -> new LinkedHashMap<>(jpaProperties.getProperties()),
                null
        );

        return builder
                .dataSource(dataSource)
                .packages("dio.marketplace.ticketing")
                .persistenceUnit("ticketing")
                .build();
    }

/**
 * Executa a operação ticketingTransactionManager, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param emf parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean
    public PlatformTransactionManager ticketingTransactionManager(@Qualifier("ticketing") LocalContainerEntityManagerFactoryBean emf){
        return new JpaTransactionManager(emf.getObject());
    }

/**
 * Executa a operação ticketingRedisConnectionFactory, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param ("${ticketing.redis.host}" parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    public RedisConnectionFactory ticketingRedisConnectionFactory(@Value("${ticketing.redis.host}") String hostName,
                                                @Value("${ticketing.redis.port}") int port) {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(hostName, port));
    }

/**
 * Executa a operação ticketingRedisTemplate, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param connectionFactory parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    public RedisTemplate<?, ?> ticketingRedisTemplate(@Qualifier("ticketing") RedisConnectionFactory connectionFactory){
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
