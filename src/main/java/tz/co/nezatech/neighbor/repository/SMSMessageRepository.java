package tz.co.nezatech.neighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tz.co.nezatech.neighbor.model.Role;
import tz.co.nezatech.neighbor.model.SMSMessage;

import java.util.List;
import java.util.Optional;

@Repository
public interface SMSMessageRepository extends JpaRepository<SMSMessage, Long> {
    List<SMSMessage> findByMsisdn(String msisdn);
}
