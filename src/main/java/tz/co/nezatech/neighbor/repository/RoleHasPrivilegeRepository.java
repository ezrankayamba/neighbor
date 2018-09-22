package tz.co.nezatech.neighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tz.co.nezatech.neighbor.model.Role;
import tz.co.nezatech.neighbor.model.RoleHasPrivilege;
import tz.co.nezatech.neighbor.model.User;

@Repository
public interface RoleHasPrivilegeRepository extends JpaRepository<RoleHasPrivilege, Long> {
    void deleteByRole(Role role);
}
