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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    // basePackages = { "br.com.net.sqlab_backend.repositories.postgres" },
    entityManagerFactoryRef = "exerciseEntityManagerFactory",
    transactionManagerRef = "exerciseTransactionManager"
)
public class configExerciseDatabase {

    @Bean
    @ConfigurationProperties(prefix = "exercise.datasource")
    public DataSource exerciseDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/db-sqlab?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC")
            .username("root")
            .password("12345")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build();
    }

    // PARA USAR JDBC NAS QUERIES AO INVÉS DE JPA
    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(exerciseDataSource());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean exerciseEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("exerciseDataSource") DataSource dataSource
    ) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql", "true"); // Exibe SQL no console
        properties.put("hibernate.format_sql", "true"); // Formatar sql no console
        // properties.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");
        
        return builder
            .dataSource(dataSource)
            .packages("br.com.net.sqlab_backend.models.exercises")  // DEFINE AS ENTIDADES USADAS PARA CRIAR AS TABELAS
            .persistenceUnit("mysqlPU")
            .properties(properties)
            .build();
    }

    // @Bean
    // public PlatformTransactionManager exerciseTransactionManager(@Qualifier("exerciseEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    //     return new JpaTransactionManager(entityManagerFactory);
    // }

    @Bean(name = "exerciseTransactionManager")
    public PlatformTransactionManager exerciseTransactionManager(
        @Qualifier("exerciseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
}
}

