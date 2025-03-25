package br.com.net.sqlab_backend.repositories.app;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.net.sqlab_backend.models.app.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
