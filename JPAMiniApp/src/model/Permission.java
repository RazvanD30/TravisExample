package model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "permissions")
@NamedQueries(
@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
    private List<User> users;

    @Column(name = "can_add_users")
    private boolean canAddUsers;

    @Column(name = "can_remove_users")
    private boolean canRemoveUsers;

    public Permission() {
    }

    public Permission(boolean canAddUsers, boolean canRemoveUsers) {
        this.canAddUsers = canAddUsers;
        this.canRemoveUsers = canRemoveUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean canAddUsers() {
        return canAddUsers;
    }

    public void setCanAddUsers(boolean canAdd) {
        this.canAddUsers = canAdd;
    }

    public boolean canRemoveUsers() {
        return canRemoveUsers;
    }

    public void setCanRemoveUsers(boolean canRemove) {
        this.canRemoveUsers = canRemove;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", canAdd=" + canAddUsers +
                ", canRemove=" + canRemoveUsers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return id == that.id &&
                canAddUsers == that.canAddUsers &&
                canRemoveUsers == that.canRemoveUsers;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, canAddUsers, canRemoveUsers);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
