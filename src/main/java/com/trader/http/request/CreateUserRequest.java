package com.trader.http.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class CreateUserRequest {
    private String email;
    private String password;

    @JsonCreator
    CreateUserRequest(Map<String, Object> values) {
        Class<CreateUserRequest> self = (Class<CreateUserRequest>) this.getClass();

        values.forEach((String fieldName, Object value) -> {
            String fieldNameToTitleCase = StringUtils.uncapitalize(fieldName);

            try {
                Field field = self.getDeclaredField(fieldNameToTitleCase);
                field.set(this, field.getType().cast(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // invalid field
            }
        });
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
