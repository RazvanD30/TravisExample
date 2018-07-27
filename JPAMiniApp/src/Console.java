import model.AddUserOperation;
import model.Permission;
import model.RemoveUserOperation;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Console {

    BufferedReader input;
    User loggedUser = null;


    private EntityManager entityManager;

    public Console(EntityManager em){
        this.entityManager = em;
        input = new BufferedReader(new InputStreamReader(System.in));

    }

    public void runConsole(){
        try {
            if(loggedUser != null)
                optionMenu();
            printLoginOptions();
            String inputString = input.readLine();
            int choice = Integer.parseInt(inputString);

            switch(choice){
                case 0:
                    System.exit(0);
                case 1:
                    login();
                    optionMenu();
                    break;
                default:
                    System.out.println("Option not implemented.");
            }

        }
        catch (Exception e) {
            System.out.println("Input is not valid.");
        }
        finally {
            runConsole();
        }
    }

    private void optionMenu() throws IOException{
        printUserOptions();
        String inputString = input.readLine();
        int choice = Integer.parseInt(inputString);

        switch (choice){
            case 0:
                loggedUser = null;
                runConsole();
                break;
            case 1:
                if(loggedUser.getPermission().canAddUsers()){
                    addUser();
                    break;
                }
            case 2:
                if(loggedUser.getPermission().canRemoveUsers()){
                    removeUser();
                    break;
                }
            default:
                System.out.println("Option not available. Maybe you don't have the required permission.");
                optionMenu();

        }



    }

    private void addUser() throws IOException{

        System.out.println("Name: ");
        String name = input.readLine();
        System.out.println("Username: ");
        String username = input.readLine();
        System.out.println("Password: ");
        String password = input.readLine();
        AddUserOperation addOp = new AddUserOperation(name,username,password);
        addOp.setUser(loggedUser);
        entityManager.getTransaction().begin();
        addOp.execute(entityManager);
        entityManager.persist(addOp);
        entityManager.getTransaction().commit();

    }

    private void removeUser() throws IOException{
        Query q = entityManager.createNamedQuery("User.getAll",User.class);
        List<User> users = q.getResultList();
        users.remove(loggedUser);
        System.out.println("Available users: ");
        users.forEach(x -> System.out.println("     " + x));
        System.out.println("Choose user id: ");
        String inputString = input.readLine();
        int id = Integer.parseInt(inputString);
        RemoveUserOperation remOp = new RemoveUserOperation();
        remOp.setUserId(id);
        remOp.setUser(loggedUser);
        entityManager.getTransaction().begin();
        remOp.execute(entityManager);
        entityManager.persist(remOp);
        entityManager.getTransaction().commit();

    }


    private void login() throws IOException{
        System.out.println("Username: ");
        String username = input.readLine();
        System.out.println("Password");
        String password = input.readLine();

        Query q = entityManager.createNamedQuery("User.getByUsernameAndPass",User.class);
        q.setParameter("username",username);
        q.setParameter("password",password);
        User user = (User) q.getSingleResult();
        if( user == null ){
            System.out.println("Login failed.");
            login();
        }
        this.loggedUser = user;
    }

    private void printLoginOptions(){
        System.out.println("0 - Exit application");
        System.out.println("1 - Login");
    }

    private void printUserOptions(){
        Permission p = this.loggedUser.getPermission();
        String msg = "0 - Logout";
        msg += p.canAddUsers() ? "\n1 - Add user" : "";
        msg += p.canRemoveUsers() ? "\n2 - Remove user" : "";
        System.out.println(msg);
    }



}
