package org.example.usersystem.anotations;

import jakarta.validation.ConstraintValidatorContext;

public final class AnnotationsUtil {

    private AnnotationsUtil () {}

    public static void setErrorMessage (ConstraintValidatorContext context, String errorMessage) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    }
}
