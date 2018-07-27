package model;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("ADD")
public class AddUserOperation extends Operation {


    @Transient
    private String name;
    @Transient
    private String username;
    @Transient
    private String password;

    private boolean successful;

    public AddUserOperation(){}

    public AddUserOperation(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute(EntityManager em) {
        successful = false;
        if( !em.getTransaction().isActive() )
            return; // should normally throw an exception
        Permission p = user.getPermission();
        if( !p.canAddUsers() )
            return; // should normally throw an exception

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPasssword(password);
        user.setPermission(p);
        em.persist(user);
        successful = true;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
