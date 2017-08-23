package com.trader.service.user;

import com.trader.service.user.exception.InvalidCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        return this.repository.save(user);
    }

    public User create(String email, String password) {
        return this.save(new User(email, password));
    }

    public String getToken(String email, String password) throws InvalidCredentialsException {
        User user = this.repository.getByEmail(email);

        if (user != null && user.checkPassword(password)) {
            user.generateToken();
            this.save(user);

            return user.getToken();
        }

        throw new InvalidCredentialsException();
    }
}
