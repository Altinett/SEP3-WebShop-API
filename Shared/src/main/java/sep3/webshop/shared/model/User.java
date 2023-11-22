package sep3.webshop.shared.model;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private int id;
    private String username, password, email, firstname, lastname;
    private Date dob;

    public User() {}

    public User(String username, String password, String email, String firstname, String lastname, Date dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
    }
    public User(int id, String username, String password, String email, String firstname, String lastname, Date dob) {
        this(username, password, email, firstname, lastname, dob);
        this.id = id;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
