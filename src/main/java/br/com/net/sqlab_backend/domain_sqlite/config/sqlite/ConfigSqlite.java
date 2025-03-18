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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

// @Configuration
// @EnableTransactionManagement
// @EnableJpaRepositories(
//     entityManagerFactoryRef = "sqliteEntityManagerFactory",
//     transactionManagerRef = "sqliteTransactionManager",
//     basePackages = { "br.com.net.sqlab_backend.domain_sqlite.repositories.sqlite" }
// )
public class ConfigSqlite {

    // @Bean(name = "sqliteDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.sqlite")
    // public DataSource dataSource() {
    //     return DataSourceBuilder.create()
    //     .url("jdbc:sqlite:./database/my_db_tests.sqlite")
    //     .driverClassName("org.sqlite.JDBC")
    //     .build();

    //     // HikariConfig config = new HikariConfig();
    //     // config.setJdbcUrl("jdbc:sqlite:./database/my_db_tests.sqlite");
    //     // config.setDriverClassName("org.sqlite.JDBC");
    //     // return new HikariDataSource(config);
    // }

    // @Bean("sqliteEntityManagerFactory")
    // public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    //     EntityManagerFactoryBuilder builder, 
    //     @Qualifier("sqliteDataSource") DataSource dataSource
    // ) {
        
    //     Map<String, String> properties = new HashMap<>();
    //     properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");

    //     properties.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");

    //     return builder
    //         .dataSource(dataSource)
    //         .packages("br.com.net.sqlab_backend.domain_sqlite.models.sqlite")
    //         .persistenceUnit("sqlitePU")
    //         .properties(properties)
    //         .build();
    // }

    // @Bean(name = "sqliteTransactionManager")
    // public PlatformTransactionManager transactionManager(@Qualifier("sqliteEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    //     return new JpaTransactionManager(entityManagerFactory);
    // }

}
