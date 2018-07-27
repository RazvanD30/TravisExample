import model.AddUserOperation;
import model.Permission;
import model.RemoveUserOperation;
import model.User;

import javax.persistence.*;
import java.util.List;






/*
    Basic application that lets users add/remove other users based on their permissions (no UI added)
    WILL NOT CREATE THE DB (name = 'jpq_mini_app') !!!
        users - table that contains all the users in the system
        operations - table that contains (logs) all the successful/failed operations performed
        permissions - table that contains compound permissions for each user (one of the entries should represent
            the set of permissions associated to a role)
 */


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("UserOperations");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        // This transaction will fill the 'users' table with 10 entries and 'permissions' with one entry.
        // Associates the permission to each user added
        transaction.begin();
        {
            Permission p = new Permission();
            p.setCanAddUsers(true);
            p.setCanRemoveUsers(false);
            entityManager.persist(p);

            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setPermission(p);
                user.setName("NAME_" + i);
                user.setUsername("USERNAME_" + i);
                user.setPasssword("PASSWORD_" + i);
                entityManager.persist(user);
            }
        }
        transaction.commit();


        // Transaction will add a new entry to the 'operations' table (addOperation) and execute the operation
        // (since it is successful it will add a new user to the 'users' table and set the boolean 'successful'
        // in 'operations' table to true.
        transaction.begin();
        {
            Query namedQuery = entityManager.createNamedQuery("User.getOne", User.class);
            namedQuery.setParameter("id", 1);
            User loggedUser = (User) namedQuery.getSingleResult();
            AddUserOperation addOp = new AddUserOperation();
            addOp.setName("NAME: ADDED BY TRANSACTION NO 2");
            addOp.setUsername("USERNAME: ADDED BY TRAN 2");
            addOp.setPassword("PASS: ADDED BY TRAN 2");
            addOp.setUser(loggedUser);
            addOp.execute(entityManager);
            entityManager.persist(addOp);
        }
        transaction.commit();

        // Transaction will add a new entry to the 'operations' table (removeOperation) and attempt to execute it.
        // Since the user doesn't have the required permission no modification on the 'users' table will be performed
        // and the boolean 'successful' in 'operations' table will be set to false.
        transaction.begin();
        {
            Query namedQuery = entityManager.createNamedQuery("User.getOne",User.class);
            namedQuery.setParameter("id",2);
            User loggedUser = (User) namedQuery.getSingleResult();
            RemoveUserOperation remOp = new RemoveUserOperation();
            remOp.setUserId(3);
            remOp.setUser(loggedUser);
            remOp.execute(entityManager);
            entityManager.persist(remOp);

        }
        transaction.commit();



        // Adds a new permission to the 'permissions' table and a new user with that permission to the 'users' table.
        transaction.begin();
        {
            Permission p = new Permission();
            p.setCanAddUsers(true);
            p.setCanRemoveUsers(true);
            entityManager.persist(p);
            User user = new User();
            user.setPermission(p);
            user.setName("ADMIN");
            user.setUsername("ADMIN");
            user.setPasssword("ADMIN");
            user.setPermission(p);
            entityManager.persist(user);
        }
        transaction.commit();

        // Executes the named query that returns the list of all users that have both read and write permissions, then
        // prints it.
        transaction.begin();
        {
            Query namedQuery = entityManager.createNamedQuery("User.getAllWithAllPermissions",User.class);
            List<User> admins = namedQuery.getResultList();
            System.out.print("Admins: ");
            admins.forEach(System.out::println);
        }
        transaction.commit();

        Console console = new Console(entityManager);
        console.runConsole();

    }
}
