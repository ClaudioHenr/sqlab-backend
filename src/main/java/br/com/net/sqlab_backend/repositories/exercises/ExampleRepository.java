package br.com.net.sqlab_backend.repositories.exercises;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class ExampleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public ExampleRepository(JdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    public void insert(String name, int age) {
        // Definição da transação
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // Inicia a transação
        TransactionStatus status = transactionManager.getTransaction(def);

        // Executa a query
        String sql = "INSERT INTO exercise_example (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);


        transactionManager.rollback(status);
        // Confirmação da transação
        // transactionManager.commit(status);
    }

}
