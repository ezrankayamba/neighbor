package tz.co.nezatech.neighbor.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_role")
public class Role extends BaseModel{
    private String name, terminal;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.LAZY)
    @JoinTable(name = "tbl_role_has_privilege",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private List<Privilege> privileges;

    public Role() {
    }

    public Role(String name, String terminal) {
        this(name, terminal, new ArrayList<>());
    }

    public Role(String name, String terminal, List<Privilege> privileges) {
        this.name = name;
        this.terminal = terminal;
        this.privileges = privileges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}
