package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "operations_log")
public class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    protected User user;

    public void execute(EntityManager em){
        EntityTransaction transaction = em.getTransaction();

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
