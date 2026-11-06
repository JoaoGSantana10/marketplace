package dio.marketplace.ticketing.infrastructure;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
public class TicketingConfiguration {
    //Configuração do DataSource o Spring vai injetar quando tiver a utilização do qualifier ticketing
    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("ticketing.datasource")
    public DataSourceProperties ticketingDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("ticketing.datasource.configuration")
    public HikariDataSource ticketingDataSource(@Qualifier("ticketing") DataSourceProperties properties){
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Qualifier("ticketing")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("ticketing.jpa")
    public JpaProperties ticketingJpaProperties(){
        return new JpaProperties();
    }

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

    @Qualifier("ticketing")
    @Bean
    public PlatformTransactionManager ticketingTransactionManager(@Qualifier("ticketing") LocalContainerEntityManagerFactoryBean emf){
        return new JpaTransactionManager(emf.getObject());
    }
}
