package model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.getOne", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.getAllWithAllPermissions",
                query = "SELECT u FROM User u " +
                        "INNER JOIN Permission p ON u.permission = p " +
                        "WHERE p.canAddUsers = TRUE AND p.canRemoveUsers = TRUE"),
        @NamedQuery(name = "User.getByUsernameAndPass", query ="SELECT u FROM User u WHERE u.username=:username AND u.passsword = :password")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(unique = true)
    private String username;
    private String passsword;


    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Permission permission;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Operation> operations;

    public User(){

    }

    public User(String name, String username, String passsword) {
        this.name = name;
        this.username = username;
        this.passsword = passsword;
    }

    public int  getId() {
        return this.id;
    }

    public void setId(int  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }


    @Override
    public String toString() {
        return "model.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", passsword='" + passsword + '\'' +
                ", permission='" + permission.toString() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(username, user.username) &&
                Objects.equals(passsword, user.passsword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, username, passsword);
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}