package org.example.usersystem.anotations.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.usersystem.constants.Messages;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default Messages.INVALID_PASSWORD_FORMAT;

    int minLength() default 6;

    int maxLength() default 50;

    boolean containsLowerCase() default false;

    boolean containsUpperCase() default false;

    boolean containsDigit() default false;

    boolean containsSpecialSymbol() default false;

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
