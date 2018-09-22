package tz.co.nezatech.neighbor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.nezatech.neighbor.model.Privilege;
import tz.co.nezatech.neighbor.model.Role;
import tz.co.nezatech.neighbor.model.RoleHasPrivilege;
import tz.co.nezatech.neighbor.model.User;
import tz.co.nezatech.neighbor.repository.PrivilegeRepository;
import tz.co.nezatech.neighbor.repository.RoleHasPrivilegeRepository;
import tz.co.nezatech.neighbor.repository.RoleRepository;
import tz.co.nezatech.neighbor.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepos;
    @Autowired
    private RoleRepository roleRepos;
    @Autowired
    private PrivilegeRepository privilegeRepos;
    @Autowired
    private RoleHasPrivilegeRepository rhpRepos;

    public void savePrivilege(Privilege privilege) {
        privilegeRepos.save(privilege);
    }
    public void saveRole(Role role) {
        List<Privilege> list = role.getPrivileges();
        role.setPrivileges(new ArrayList<>());
        Role save = roleRepos.save(role);
        rhpRepos.deleteByRole(save);
        list.forEach(privilege -> rhpRepos.save(new RoleHasPrivilege(save, privilege)));
    }
    public void saveUser(User user){
        userRepos.save(user);
    }
    public List<User> users(){
        return userRepos.findAll();
    }

    public List<Privilege> privileges() {
        return privilegeRepos.findAll();
    }

    public List<Role> roles() {
        return roleRepos.findAll();
    }

    public Role roleByName(String name) {
        return roleRepos.findByName(name).orElse(null);
    }
}
