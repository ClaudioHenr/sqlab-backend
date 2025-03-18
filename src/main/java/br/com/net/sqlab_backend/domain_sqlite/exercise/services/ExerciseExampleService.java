package br.com.net.sqlab_backend.domain_sqlite.exercise.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class ExerciseExampleService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource exerciseDataSource;

    public ExerciseExampleService(
        @Qualifier("exerciseJdbcTemplate") JdbcTemplate jdbcTemplate,
        @Qualifier("exerciseDataSource") DataSource exerciseDataSource
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.exerciseDataSource = exerciseDataSource;
    }

    public void createExerciseExampleTable(JdbcTemplate tempJdbcTemplate) {
        // 🔥 Remove a tabela temporária se ela já existir
        String dropTableSql = "DROP TABLE IF EXISTS temp_exercise";
        tempJdbcTemplate.execute(dropTableSql);
        
        String sql = """
            CREATE TEMP TABLE temp_exercise (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL
            )
        """;
        tempJdbcTemplate.execute(sql);
        System.out.println("Tabela temporária de exercicios criada no SQLite!");
    }

    public void insertTempUser(JdbcTemplate tempJdbcTemplate) {
        String sql = "INSERT INTO temp_exercise (nome, email) VALUES (?, ?)";
        tempJdbcTemplate.update(sql, "Usuário Temp", "tempuser@example.com");
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão SQLite fechada antes do encerramento!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getDataExerciseTest(String query) {
        Connection conn = null;
        try {
            // Obtém uma nova conexão
            conn = DataSourceUtils.getConnection(this.exerciseDataSource);

            // Cria um DataSource que usa essa conexão
            SingleConnectionDataSource singleConnDataSource = new SingleConnectionDataSource(conn, true);
            
            // Cria um JdbcTemplate usando a MESMA conexão
            JdbcTemplate tempJdbcTemplate = new JdbcTemplate(singleConnDataSource);

            // Cria a tabela temporária
            createExerciseExampleTable(tempJdbcTemplate);

            // Insere um registro temporário
            insertTempUser(tempJdbcTemplate);

            // Executa a consulta
            List<Map<String, Object>> result = tempJdbcTemplate.queryForList(query);
            System.out.println("Resultado da consulta: " + result);

            return result;
        } finally {
            closeConnection(conn); // Garante que a conexão seja fechada após a requisição
        }
    }
}
