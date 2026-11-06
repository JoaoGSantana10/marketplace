package dio.marketplace.catalog;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(
        basePackages = "dio.marketplace.catalog",
        entityManagerFactoryRef = "catalogEntityManagerFactory",
        transactionManagerRef = "catalogTransactionManager")
/**
 * Class CatalogConfiguration do módulo marketplace.
 * Representa o contrato, modelo ou componente indicado pelo próprio nome da declaração.
 * Mantém as regras e responsabilidades descritas pela implementação, sem expor detalhes de infraestrutura desnecessários aos consumidores.
 */
@EnableMongoRepositories
@EnableMongoAuditing
public class CatalogConfiguration {

/**
 * Executa a operação catalogDataSourceProperties, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    @Qualifier("catalog")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("catalog.datasource")
    public DataSourceProperties catalogDataSourceProperties() {
        return new DataSourceProperties();
    }

/**
 * Executa a operação catalogDataSource, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param properties parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("catalog")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("catalog.datasource.configuration")
    public HikariDataSource catalogDataSource(@Qualifier("catalog") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

/**
 * Executa a operação catalogJpaProperties, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @return resultado produzido pela operação.
 */
    @Qualifier("catalog")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("catalog.jpa")
    public JpaProperties catalogJpaProperties() {
        return new JpaProperties();
    }

/**
 * Executa a operação catalogEntityManagerFactory, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param ("catalog" parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("catalog")
    @Bean(defaultCandidate = false)
    public LocalContainerEntityManagerFactoryBean catalogEntityManagerFactory(@Qualifier("catalog") DataSource dataSource,
                                                                              @Qualifier("catalog") JpaProperties jpaProperties) {
        var builder = new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                x -> new LinkedHashMap<>(jpaProperties.getProperties()),
                null
        );

        return builder
                .dataSource(dataSource)
                .packages("dio.marketplace.catalog")
                .persistenceUnit("catalog")
                .build();
    }

/**
 * Executa a operação catalogTransactionManager, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param emf parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Qualifier("catalog")
    @Bean
    public PlatformTransactionManager catalogTransactionManager(@Qualifier("catalog") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }

/**
 * Executa a operação catalogRedisConnectionFactory, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param ("${catalog.redis.host}" parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Primary
    @Bean
    public RedisConnectionFactory catalogRedisConnectionFactory(@Value("${catalog.redis.host}") String hostName,
                                                                @Value("${catalog.redis.port}") int port) {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(hostName, port));
    }

/**
 * Executa a operação catalogCacheManager, conforme o papel desta classe no fluxo da aplicação.
 * A operação preserva as invariantes do domínio e delega persistência ou integração aos componentes apropriados.
 * @param connectionFactory parâmetro utilizado pela operação.
 * @return resultado produzido pela operação.
 */
    @Primary
    @Bean
    public RedisCacheManager catalogCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory).build();
    }


}
