package br.com.net.sqlab_backend.domain_sqlite.repositories.sqlite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.net.sqlab_backend.domain_sqlite.models.sqlite.TestSqLite;

@Repository
public interface TestSqliteRepository extends JpaRepository<TestSqLite, Long> {
    
}
