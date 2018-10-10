package tz.co.nezatech.neighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tz.co.nezatech.neighbor.model.Role;
import tz.co.nezatech.neighbor.model.Token;
import tz.co.nezatech.neighbor.model.User;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String name);
}
