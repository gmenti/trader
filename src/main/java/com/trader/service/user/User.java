package com.trader.service.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"token"}),
})
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Length(max = 100)
    private String token;

    @NotNull
    @Email
    @Length(min = 3, max = 255)
    private String email;

    @NotNull
    @Length(max = 255)
    private String password;

    protected User() {
        //
    }

    User(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
        this.generateToken();
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }
}
