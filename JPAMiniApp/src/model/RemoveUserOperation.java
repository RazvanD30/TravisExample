package model;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("REMOVE")
public class RemoveUserOperation extends Operation{

    @Transient
    private int userId;

    private boolean successful;

    @Override
    public void execute(EntityManager em) {
        successful = false;
        if( !em.getTransaction().isActive() )
            return; // should normally throw an exception
        Permission p = user.getPermission();
        if( !p.canRemoveUsers() )
            return; // should normally throw an exception
        em.remove(em.find(User.class,userId));
        successful = true;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
