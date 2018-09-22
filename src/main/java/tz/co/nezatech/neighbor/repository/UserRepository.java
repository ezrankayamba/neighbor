package tz.co.nezatech.neighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tz.co.nezatech.neighbor.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
