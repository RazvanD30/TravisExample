package edu.msg.ro.persistence.user.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Transient
    private final static int MAX_STRING_LENGTH = 20;

    @Column(name = "firstName", length = MAX_STRING_LENGTH, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = MAX_STRING_LENGTH, nullable = false)
    private String lastName;

    //TODO ADD VALIDATOR (ONLY RO AND DE NUMBERS)
    @Column(name = "mobileNumber", length = MAX_STRING_LENGTH, nullable = false)
    private String mobileNumber;

    //TODO CONSTRAINT PE FORMAT
    @Column(name = "email", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String email;

    //TODO GENERAT AUTOMAT
    @Column(name = "username", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = MAX_STRING_LENGTH, nullable = false)
    private String password;

    @Column(name = "status", length = MAX_STRING_LENGTH, nullable = false)
    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = { @JoinColumn(name = "rid")})
    private List<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, String mobileNumber, String email,
                String username, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public User(String firstName, String lastName, String mobileNumber, String email,
                String username, String password, String status, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(mobileNumber, user.mobileNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, mobileNumber, email, username, password, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
