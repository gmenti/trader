package com.trader.service.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
})
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Email
    @Length(min = 3, max = 255)
    private String email;

    @NotNull
    @Length(min = 8, max = 50)
    private String password;

    protected User() {
        //
    }

    public User(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
