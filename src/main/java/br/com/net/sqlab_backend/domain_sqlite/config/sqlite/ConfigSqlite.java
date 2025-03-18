package br.com.net.sqlab_backend.domain_sqlite.config.sqlite;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = { "br.com.net.sqlab_backend.domain_sqlite.repositories.sqlite" },
    entityManagerFactoryRef = "exerciseEntityManagerFactory",
    transactionManagerRef = "exerciseTransactionManager"
)
public class ConfigSqlite {

    @Bean
    @ConfigurationProperties(prefix = "exercise.datasource")
    public DataSource exerciseDataSource() {
        return DataSourceBuilder.create()
        .url("jdbc:sqlite:./database/my_db_tests.sqlite")  // pode ser trocado para .db
        .driverClassName("org.sqlite.JDBC")
        .build();
    }

    @Bean
    public JdbcTemplate exerciseJdbcTemplate(@Qualifier("exerciseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean exerciseEntityManagerFactory(
        EntityManagerFactoryBuilder builder, 
        @Qualifier("exerciseDataSource") DataSource dataSource
    ) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        properties.put("hibernate.show_sql", "true"); // Exibe SQL no console
        properties.put("hibernate.format_sql", "true"); // Formatar sql no console
        // properties.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");

        return builder
            .dataSource(dataSource)
            .packages("br.com.net.sqlab_backend.domain_sqlite.models.sqlite")
            .persistenceUnit("sqlitePU")
            .properties(properties)
            .build();
    }

    @Bean
    public PlatformTransactionManager exerciseTransactionManager(@Qualifier("exerciseEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
