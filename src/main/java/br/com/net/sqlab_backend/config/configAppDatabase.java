package br.com.net.sqlab_backend.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = { "br.com.net.sqlab_backend.repositories.app" },  // DEFINE OS REPOSITORIOS UTILIZADOS
    entityManagerFactoryRef = "appEntityManagerFactory",
    transactionManagerRef = "appTransactionManager"
)
public class configAppDatabase {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource authDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/db-sqlab")
            .driverClassName("org.postgresql.Driver")
            .username("postgres")
            .password("12345")
            .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("authDataSource") DataSource dataSource
    ) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true"); // Exibe SQL no console
        properties.put("hibernate.format_sql", "true"); // Formatar sql no console
        // properties.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");
        
        return builder
            .dataSource(dataSource)
            .packages("br.com.net.sqlab_backend.models.app")  // DEFINE AS ENTIDADES USADAS PARA CRIAR AS TABELAS
            .persistenceUnit("postgresPU")
            .properties(properties)
            .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager appTransactionManager(@Qualifier("appEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

