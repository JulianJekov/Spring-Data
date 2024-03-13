package org.example.usersystem.anotations.email;

import jakarta.validation.Constraint;

import jakarta.validation.Payload;
import org.example.usersystem.constants.Messages;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = EmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})


public @interface Email {
    String message() default Messages.INVALID_EMAIL_FORMAT;

    int minUserNameLength() default 1;

    int maxUsernameLength() default 50;

    int maxHostNameLength() default 50;

    String regex() default "^([a-zA-Z0-9][-_.]?)*[a-zA-Z0-9]@([a-zA-Z][-]?)*[a-zA-Z](\\.([a-zA-Z][-]?)*[a-zA-Z])+$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
